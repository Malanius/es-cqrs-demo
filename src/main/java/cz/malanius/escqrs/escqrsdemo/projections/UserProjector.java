package cz.malanius.escqrs.escqrsdemo.projections;

import cz.malanius.escqrs.escqrsdemo.events.*;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.repository.UserReadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserProjector {

    private final UserReadRepository readRepository;

    @Autowired
    public UserProjector(UserReadRepository readRepository) {
        this.readRepository = readRepository;
    }

    public void project(UUID userId, List<Event> events) {
        events.forEach(event -> {
            if (event instanceof UserAddressAddedEvent)
                apply(userId, (UserAddressAddedEvent) event);
            if (event instanceof UserAddressRemovedEvent)
                apply(userId, (UserAddressRemovedEvent) event);
            if (event instanceof UserContactAddedEvent)
                apply(userId, (UserContactAddedEvent) event);
            if (event instanceof UserContactRemovedEvent)
                apply(userId, (UserContactRemovedEvent) event);
        });
    }

    private void apply(UUID userId, UserAddressAddedEvent event) {
        AddressDTO address = AddressDTO.builder()
                .state(event.getState())
                .city(event.getCity())
                .postcode(event.getPostCode())
                .build();
        UserAddresses userAddresses = Optional.ofNullable(readRepository.getUserAddresses().get(userId))
                .orElse(new UserAddresses());
        Set<AddressDTO> addresses = Optional.ofNullable(userAddresses.getAddressByRegion().get(address.getState()))
                .orElse(new HashSet<>());
        addresses.add(address);
        userAddresses.getAddressByRegion().put(address.getState(), addresses);
        readRepository.getUserAddresses().put(userId, userAddresses);
    }

    private void apply(UUID userId, UserAddressRemovedEvent event) {
        AddressDTO address = AddressDTO.builder()
                .state(event.getState())
                .city(event.getCity())
                .postcode(event.getPostCode())
                .build();
        UserAddresses userAddresses = readRepository.getUserAddresses().get(userId);
        if (userAddresses != null) {
            Set<AddressDTO> addresses = userAddresses.getAddressByRegion()
                    .get(address.getState());
            if (addresses != null)
                addresses.remove(address);
            readRepository.getUserAddresses().put(userId, userAddresses);
        }
    }

    private void apply(UUID userId, UserContactAddedEvent event) {
        ContactDTO contact = ContactDTO.builder()
                .type(event.getContactType())
                .detail(event.getContactDetails())
                .build();
        UserContacts userContacts = Optional.ofNullable(readRepository.getUserContacts().get(userId))
                .orElse(new UserContacts());
        Set<ContactDTO> addresses = Optional.ofNullable(userContacts.getContactsByType().get(contact.getType()))
                .orElse(new HashSet<>());
        addresses.add(contact);
        userContacts.getContactsByType().put(contact.getType(), addresses);
        readRepository.getUserContacts().put(userId, userContacts);
    }

    private void apply(UUID userId, UserContactRemovedEvent event) {
        ContactDTO contact = ContactDTO.builder()
                .type(event.getContactType())
                .detail(event.getContactDetails())
                .build();
        UserContacts userContacts = readRepository.getUserContacts().get(userId);
        if (userContacts != null) {
            Set<ContactDTO> contacts = userContacts.getContactsByType()
                    .get(contact.getType());
            if (contacts != null)
                contacts.remove(contact);
            readRepository.getUserContacts().put(userId, userContacts);
        }
    }


}
