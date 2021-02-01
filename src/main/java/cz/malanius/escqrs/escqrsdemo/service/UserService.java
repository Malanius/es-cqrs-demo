package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.aggregates.UserAggregate;
import cz.malanius.escqrs.escqrsdemo.commands.CreateUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.UpdateUserCommand;
import cz.malanius.escqrs.escqrsdemo.events.Event;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.projections.UserAddresses;
import cz.malanius.escqrs.escqrsdemo.projections.UserContacts;
import cz.malanius.escqrs.escqrsdemo.projections.UserProjector;
import cz.malanius.escqrs.escqrsdemo.repository.UserReadRepository;
import cz.malanius.escqrs.escqrsdemo.store.EventStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final EventStore store;
    private final UserProjector projector;
    private final UserAggregate aggregate;
    private final UserReadRepository readRepository;

    @Autowired
    public UserService(EventStore store, UserProjector projector, UserAggregate aggregate, UserReadRepository readRepository) {
        this.store = store;
        this.projector = projector;
        this.aggregate = aggregate;
        this.readRepository = readRepository;
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
        List<Event> events = aggregate.handleCreateUserCommand(CreateUserCommand.builder()
                .userId(userId)
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build());
        projector.project(userId, events);
        return UserUtility.recreateUserState(store, userId);
    }

    public void deleteUser(UUID userId) {
        // TODO
    }

    public UserDTO updateUserAddresses(UUID userId, Set<AddressDTO> addresses) {
        UserDTO user = UserUtility.recreateUserState(store, userId);
        List<Event> events = aggregate.handleUpdateUserCommand(UpdateUserCommand.builder()
                .userId(userId)
                .addresses(addresses)
                .contacts(user.getContacts())
                .build());
        projector.project(userId, events);
        return UserUtility.recreateUserState(store, userId);
    }

    public UserDTO updateUserContacts(UUID userId, Set<ContactDTO> contacts) {
        UserDTO user = UserUtility.recreateUserState(store, userId);
        List<Event> events = aggregate.handleUpdateUserCommand(UpdateUserCommand.builder()
                .userId(userId)
                .addresses(user.getAddresses())
                .contacts(contacts)
                .build());
        projector.project(userId, events);
        return UserUtility.recreateUserState(store, userId);
    }

    public Set<ContactDTO> getContactsOfType(UUID userId, String type) {
        UserContacts contacts = Optional.ofNullable(readRepository.getUserContacts().get(userId))
                .orElse(new UserContacts());
        return contacts.getContactsByType().get(type);
    }

    public Set<AddressDTO> getAddressesForRegion(UUID userId, String region) {
        UserAddresses addresses = Optional.ofNullable(readRepository.getUserAddresses().get(userId))
                .orElse(new UserAddresses());
        return addresses.getAddressByRegion().get(region);
    }

}

