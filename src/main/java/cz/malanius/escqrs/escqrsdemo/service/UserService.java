package cz.malanius.escqrs.escqrsdemo.service;

import cz.malanius.escqrs.escqrsdemo.model.entities.AddressEntity;
import cz.malanius.escqrs.escqrsdemo.model.entities.ContactEntity;
import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import cz.malanius.escqrs.escqrsdemo.repository.UserWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserWriteRepository repository;

    @Autowired
    public UserService(UserWriteRepository repository) {
        this.repository = repository;
    }

    public Set<UserEntity> getAllUsers() {
        Iterable<UserEntity> allUsers = repository.findAll();
        Set<UserEntity> users = new HashSet<>();
        allUsers.forEach(users::add);
        return users;
    }

    public Optional<UserEntity> getUser(UUID userId) {
        return repository.findById(userId);
    }

    public Set<ContactEntity> getContactByType(UserEntity user, String contactType) {
        Set<ContactEntity> contacts = user.getContacts();
        return contacts.stream()
                .filter(c -> c.getType().equals(contactType))
                .collect(Collectors.toSet());
    }

    public Set<AddressEntity> getAddressByRegion(UserEntity user, String state) {
        Set<AddressEntity> addresses = user.getAddresses();
        return addresses.stream()
                .filter(a -> a.getState().equals(state))
                .collect(Collectors.toSet());
    }
}

