package cz.malanius.escqrs.escqrsdemo.rest;

import cz.malanius.escqrs.escqrsdemo.aggregates.UserAggregate;
import cz.malanius.escqrs.escqrsdemo.commands.CreateUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.DeleteUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.UpdateUserCommand;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.AddressEntity;
import cz.malanius.escqrs.escqrsdemo.model.entities.ContactEntity;
import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import cz.malanius.escqrs.escqrsdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService users;
    private final UserAggregate userAggregate;

    @Autowired
    public UserController(UserService users, UserAggregate userAggregate) {
        this.users = users;
        this.userAggregate = userAggregate;
    }

    @GetMapping
    public Set<UserEntity> listUsers() {
        return users.getAllUsers();
    }

    @PostMapping("/user")
    public UserDTO createUser(@RequestBody UserDTO user) {
        CreateUserCommand command = CreateUserCommand.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .build();
        return userAggregate.handleCreateUserCommand(command);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserEntity> getUser(@PathVariable UUID id) {
        Optional<UserEntity> maybeUser = users.getUser(id);
        return maybeUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{id}")
    public void deleteUSer(@PathVariable UUID id) {
        DeleteUserCommand command = DeleteUserCommand.builder()
                .userId(id)
                .build();
        userAggregate.handleDeleteUserCommand(command);
    }

    @PostMapping("/user/{id}/addresses")
    public UserDTO setUserAddress(@PathVariable UUID id, @RequestBody Set<AddressDTO> addresses) {
        UpdateUserCommand command = UpdateUserCommand.builder()
                .userId(id)
                .addresses(addresses)
                .build();
        return userAggregate.handleUpdateUserCommand(command);
    }

    @PostMapping("/user/{id}/contacts")
    public UserDTO updateUserContacts(@PathVariable UUID id, @RequestBody Set<ContactDTO> contacts) {
        UpdateUserCommand command = UpdateUserCommand.builder()
                .userId(id)
                .contacts(contacts)
                .build();
        return userAggregate.handleUpdateUserCommand(command);
    }

    @GetMapping("/user/{id}/contacts/{type}")
    public ResponseEntity<Set<ContactEntity>> getUserContactsOfType(@PathVariable UUID id, @PathVariable String type) {
        Optional<UserEntity> maybeUser = users.getUser(id);
        return maybeUser.map(userEntity -> ResponseEntity.ok(users.getContactByType(userEntity, type)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}/addresses/{region}")
    public ResponseEntity<Set<AddressEntity>> getUserAddressesForRegion(@PathVariable UUID id, @PathVariable String region) {
        Optional<UserEntity> maybeUser = users.getUser(id);
        return maybeUser.map(userEntity -> ResponseEntity.ok(users.getAddressByRegion(userEntity, region)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
