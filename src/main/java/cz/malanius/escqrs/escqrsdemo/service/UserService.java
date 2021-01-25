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

    public Set<User> getAllUsers(){
        return repository.getUsers();
    }

    public User createUser(String firstName, String lastName) {
        User user = User.builder()
                .userId(UUID.randomUUID())
                .firstName(firstName)
                .lastName(lastName)
                .build();
        repository.saveUser(user);
        return user;
    }

    public User getUser(String userId) {
        return repository.getUser(userId);
    }

    public void deleteUSer(String userId){
        repository.removeUser(userId);
    }

    public User updateUserAddresses(String userId, Set<Address> addresses) {
        User user = repository.getUser(userId);
        user.setAddresses(addresses);
        return repository.saveUser(user);
    }

    public User updateUserContacts(String userId, Set<Contact> contacts) {
        User user = repository.getUser(userId);
        user.setContacts(contacts);
        return repository.saveUser(user);
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
