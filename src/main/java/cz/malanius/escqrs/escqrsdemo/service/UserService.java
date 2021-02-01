package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.events.*;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.repository.UserWriteRepository;
import cz.malanius.escqrs.escqrsdemo.store.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final EventStore store;

    @Autowired
    public UserService(UserWriteRepository repository, EventStore store) {
        this.store = store;
    }

    public Set<UserDTO> getAllUsers() {
        return store.getStore().keySet().stream()
                .map(id -> UserUtility.recreateUserState(store, id))
                .collect(Collectors.toSet());
    }

    public UserDTO getUser(UUID id) {
        return UserUtility.recreateUserState(store, id);
    }

    public UserDTO createUser(UserDTO user) {
        UUID userId = UUID.randomUUID();
        store.addEvent(userId, UserCreatedEvent.builder()
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build()
        );
        return UserUtility.recreateUserState(store, userId);
    }

    public void deleteUser(UUID id) {
        // TODO
    }

    public UserDTO updateUserAdresses(UUID userId, Set<AddressDTO> addresses) {
        UserDTO user = UserUtility.recreateUserState(store, userId);

        user.getAddresses().stream()
                .filter(a -> !addresses.contains(a))
                .forEach(a -> store.addEvent(
                        userId, UserAddressRemoved.builder()
                                .city(a.getCity())
                                .state(a.getState())
                                .postCode(a.getPostcode())
                                .build())
                );

        addresses.stream()
                .filter(a -> !user.getAddresses().contains(a))
                .forEach(a -> store.addEvent(
                        userId, UserAddressAddedEvent.builder()
                                .city(a.getCity())
                                .state(a.getState())
                                .postCode(a.getPostcode())
                                .build())
                );

        return UserUtility.recreateUserState(store, userId);
    }

    public UserDTO updateUserContacts(UUID userId, Set<ContactDTO> contacts) {
        UserDTO user = UserUtility.recreateUserState(store, userId);
        user.getContacts().stream()
                .filter(c -> !contacts.contains(c))
                .forEach(c -> store.addEvent(
                        userId, UserContactRemovedEvent.builder()
                                .contactType(c.getType())
                                .contactDetails(c.getDetail())
                                .build())
                );

        contacts.stream()
                .filter(c -> !user.getContacts().contains(c))
                .forEach(c -> store.addEvent(
                        userId, UserContactAddedEvent.builder()
                                .contactType(c.getType())
                                .contactDetails(c.getDetail())
                                .build())
                );

        return UserUtility.recreateUserState(store, userId);
    }

    public Set<ContactDTO> getContactsOfType(UUID userId, String type) {
        UserDTO user = UserUtility.recreateUserState(store, userId);
        return user.getContacts().stream()
                .filter(c -> c.getType().equals(type))
                .collect(Collectors.toSet());
    }

    public Set<AddressDTO> getAddressesForRegion(UUID userId, String region) {
        UserDTO user = UserUtility.recreateUserState(store, userId);
        return user.getAddresses().stream()
                .filter(a -> a.getState().equals(region))
                .collect(Collectors.toSet());
    }
}


