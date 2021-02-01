package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.events.*;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.store.EventStore;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

// Always re-creating object from all events is costly, but here in demo it suffices
// To prevent this, we could use snapshots of the hydrated state or cache
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserUtility {
    public static UserDTO recreateUserState(EventStore store, UUID userId) {
        List<Event> eventsForUser = store.getEventsForUser(userId);
        return eventsForUser.stream()
                .sorted(Comparator.comparing(Event::getCreated))
                .reduce(UserDTO.builder().id(userId).build(), UserUtility::applyEvent, (old, acc) -> acc);

    }

    private static UserDTO applyEvent(UserDTO user, Event event) {
        if (event instanceof UserCreatedEvent) {
            return applyUserCreated(user, (UserCreatedEvent) event);
        }
        if (event instanceof UserAddressAddedEvent) {
            return applyUserAddressAdded(user, (UserAddressAddedEvent) event);
        }
        if (event instanceof UserAddressRemoved) {
            return applyUserAddressRemoved(user, (UserAddressRemoved) event);
        }
        if (event instanceof UserContactAddedEvent) {
            return applyUSerContactAdded(user, (UserContactAddedEvent) event);
        }
        if (event instanceof UserContactRemovedEvent) {
            return applyUSerContactRemoved(user, (UserContactRemovedEvent) event);
        }
        throw new IllegalStateException("Unknown event received!");
    }

    private static UserDTO applyUserCreated(UserDTO user, UserCreatedEvent event) {
        return user.toBuilder()
                .firstName(event.getFirstName())
                .lastName(event.getLastName())
                .contacts(new HashSet<>())
                .addresses(new HashSet<>())
                .build();
    }

    private static UserDTO applyUserAddressAdded(UserDTO user, UserAddressAddedEvent event) {
        Set<AddressDTO> addresses = new HashSet<>(user.getAddresses());
        addresses.add(AddressDTO.builder()
                .city(event.getCity())
                .state(event.getState())
                .postcode(event.getPostCode())
                .build());

        return user.toBuilder()
                .addresses(addresses)
                .build();
    }

    private static UserDTO applyUserAddressRemoved(UserDTO user, UserAddressRemoved event) {
        Set<AddressDTO> addresses = new HashSet<>(user.getAddresses());
        AddressDTO removed = AddressDTO.builder()
                .city(event.getCity())
                .state(event.getState())
                .postcode(event.getPostCode())
                .build();

        Set<AddressDTO> newAddresses = addresses.stream()
                .filter(a -> !a.equals(removed))
                .collect(Collectors.toSet());

        return user.toBuilder()
                .addresses(newAddresses)
                .build();
    }

    private static UserDTO applyUSerContactAdded(UserDTO user, UserContactAddedEvent event) {
        Set<ContactDTO> contacts = new HashSet<>(user.getContacts());
        contacts.add(ContactDTO.builder()
                .type(event.getContactType())
                .detail(event.getContactDetails())
                .build());

        return user.toBuilder()
                .contacts(contacts)
                .build();
    }

    private static UserDTO applyUSerContactRemoved(UserDTO user, UserContactRemovedEvent event) {
        Set<ContactDTO> contacts = new HashSet<>(user.getContacts());
        ContactDTO removed = ContactDTO.builder()
                .type(event.getContactType())
                .detail(event.getContactDetails())
                .build();

        Set<ContactDTO> newContacts = contacts.stream()
                .filter(c -> !c.equals(removed))
                .collect(Collectors.toSet());

        return user.toBuilder()
                .contacts(newContacts)
                .build();
    }
}
