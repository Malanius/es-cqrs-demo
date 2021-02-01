package cz.malanius.escqrs.escqrsdemo.aggregates;

import cz.malanius.escqrs.escqrsdemo.commands.CreateUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.DeleteUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.UpdateUserCommand;
import cz.malanius.escqrs.escqrsdemo.events.*;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.projections.UserProjector;
import cz.malanius.escqrs.escqrsdemo.service.UserUtility;
import cz.malanius.escqrs.escqrsdemo.store.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserAggregate {

    private final EventStore store;
    private final UserProjector userProjector;

    @Autowired
    public UserAggregate(EventStore store, UserProjector userProjector) {
        this.store = store;
        this.userProjector = userProjector;
    }

    public List<Event> handleCreateUserCommand(CreateUserCommand command) {
        UserCreatedEvent event = UserCreatedEvent.builder()
                .userId(UUID.randomUUID())
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
        store.addEvent(event.getUserId(), event);
        return Collections.singletonList(event);
    }

    public List<Event> handleUpdateUserCommand(UpdateUserCommand command) {
        UserDTO user = UserUtility.recreateUserState(store, command.getUserId());
        List<Event> events = new ArrayList<>();

        List<ContactDTO> contactsToRemove = user.getContacts().stream()
                .filter(c -> !command.getContacts().contains(c))
                .collect(Collectors.toList());
        contactsToRemove.forEach(c -> {
            UserContactRemovedEvent contactRemovedEvent = UserContactRemovedEvent.builder()
                    .contactType(c.getType())
                    .contactDetails(c.getDetail())
                    .build();
            events.add(contactRemovedEvent);
            store.addEvent(command.getUserId(), contactRemovedEvent);
        });

        List<ContactDTO> contactsToAdd = command.getContacts().stream()
                .filter(c -> !user.getContacts().contains(c))
                .collect(Collectors.toList());
        contactsToAdd.forEach(c -> {
            UserContactAddedEvent contactAddedEvent = UserContactAddedEvent.builder()
                    .contactType(c.getType())
                    .contactDetails(c.getDetail())
                    .build();
            events.add(contactAddedEvent);
            store.addEvent(command.getUserId(), contactAddedEvent);
        });

        List<AddressDTO> addressToRemove = user.getAddresses().stream()
                .filter(a -> !command.getAddresses().contains(a))
                .collect(Collectors.toList());
        addressToRemove.forEach(a -> {
            UserAddressRemovedEvent addressRemovedEvent = UserAddressRemovedEvent.builder()
                    .state(a.getState())
                    .city(a.getCity())
                    .postCode(a.getPostcode())
                    .build();
            events.add(addressRemovedEvent);
            store.addEvent(command.getUserId(), addressRemovedEvent);
        });

        List<AddressDTO> addressToAdd = command.getAddresses().stream()
                .filter(a -> !user.getAddresses().contains(a))
                .collect(Collectors.toList());
        addressToAdd.forEach(a -> {
            UserAddressAddedEvent addressAddedEvent = UserAddressAddedEvent.builder()
                    .state(a.getState())
                    .city(a.getCity())
                    .postCode(a.getPostcode())
                    .build();
            events.add(addressAddedEvent);
            store.addEvent(command.getUserId(), addressAddedEvent);
        });
        return events;
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {

    }
}
