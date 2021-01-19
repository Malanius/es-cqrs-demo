package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.model.Address;
import cz.malanius.escqrs.escqrsdemo.model.Contact;
import cz.malanius.escqrs.escqrsdemo.model.User;
import cz.malanius.escqrs.escqrsdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public String createUser(String firstName, String lastName) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .firstName(firstName)
                .lastName(lastName)
                .build();
        repository.saveUser(user);
        return user.getUserId().toString();
    }

    public User getUser(String userId) {
        return repository.getUser(userId);
    }

    public void updateUser(String userId, Set<Contact> contacts, Set<Address> addresses) {
        User user = repository.getUser(userId);
        user.setContacts(contacts);
        user.setAddresses(addresses);
        repository.saveUser(user);
    }

    public Set<Contact> getContactByType(String userId, String contactType) {
        User user = repository.getUser(userId);
        Set<Contact> contacts = user.getContacts();
        return contacts.stream()
                .filter(c -> c.getType().equals(contactType))
                .collect(Collectors.toSet());
    }

    public Set<Address> getAddressByRegion(String userId, String state) {
        User user = repository.getUser(userId);
        Set<Address> addresses = user.getAddresses();
        return addresses.stream()
                .filter(a -> a.getState().equals(state))
                .collect(Collectors.toSet());
    }
}
