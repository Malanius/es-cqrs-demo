package cz.malanius.escqrs.escqrsdemo.rest;

import cz.malanius.escqrs.escqrsdemo.model.Address;
import cz.malanius.escqrs.escqrsdemo.model.Contact;
import cz.malanius.escqrs.escqrsdemo.model.User;
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

    @Autowired
    public UserController(UserService users) {
        this.users = users;
    }

    @GetMapping
    public Set<User> listUsers() {
        return users.getAllUsers();
    }

    @PostMapping("/user")
    public User createUser(@RequestBody User user) {
        return users.createUser(user.getFirstName(), user.getLastName());
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        Optional<User> maybeUser = users.getUser(UUID.fromString(id));
        return maybeUser.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/user/{id}")
    public void deleteUSer(@PathVariable String id) {
        users.deleteUSer(UUID.fromString(id));
    }

    @PostMapping("/user/{id}/addresses")
    public ResponseEntity<User> addUserAddress(@PathVariable String id, @RequestBody Set<Address> addresses) {
        Optional<User> maybeUser = users.getUser(UUID.fromString(id));
        return maybeUser.map(user -> ResponseEntity.ok(users.addUserAddresses(user, addresses)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user/{id}/contacts")
    public ResponseEntity<User> updateUserContacts(@PathVariable String id, @RequestBody Set<Contact> contacts) {
        Optional<User> maybeUser = users.getUser(UUID.fromString(id));
        return maybeUser.map(user -> ResponseEntity.ok(users.addUserContacts(user, contacts)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}/contacts/{type}")
    public ResponseEntity<Set<Contact>> getUserContactsOfType(@PathVariable String id, @PathVariable String type) {
        Optional<User> maybeUser = users.getUser(UUID.fromString(id));
        return maybeUser.map(user -> ResponseEntity.ok(users.getContactByType(user, type)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{id}/addresses/{region}")
    public ResponseEntity<Set<Address>> getUserAddressesForRegion(@PathVariable String id, @PathVariable String region) {
        Optional<User> maybeUser = users.getUser(UUID.fromString(id));
        return maybeUser.map(user -> ResponseEntity.ok(users.getAddressByRegion(user, region)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
