package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.model.Address;
import cz.malanius.escqrs.escqrsdemo.model.Contact;
import cz.malanius.escqrs.escqrsdemo.model.User;
import cz.malanius.escqrs.escqrsdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public Set<User> getAllUsers() {
        Iterable<User> allUsers = repository.findAll();
        Set<User> users = new HashSet<>();
        allUsers.forEach(users::add);
        return users;
    }

    public User createUser(String firstName, String lastName) {
        User user = User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .build();
        return repository.save(user);
    }

    public Optional<User> getUser(UUID userId) {
        return repository.findById(userId);
    }

    public void deleteUSer(UUID userId) {
        repository.deleteById(userId);
    }

    public User addUserAddresses(User user, Set<Address> addresses) {
        addresses.forEach(a -> user.getAddresses().add(a));
        return repository.save(user);
    }

    public User addUserContacts(User user, Set<Contact> contacts) {
        contacts.forEach(c -> user.getContacts().add(c));
        return repository.save(user);
    }

    public Set<Contact> getContactByType(User user, String contactType) {
        Set<Contact> contacts = user.getContacts();
        return contacts.stream()
                .filter(c -> c.getType().equals(contactType))
                .collect(Collectors.toSet());
    }

    public Set<Address> getAddressByRegion(User user, String state) {
        Set<Address> addresses = user.getAddresses();
        return addresses.stream()
                .filter(a -> a.getState().equals(state))
                .collect(Collectors.toSet());
    }
}

