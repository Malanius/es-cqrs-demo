package cz.malanius.escqrs.escqrsdemo.rest;

import cz.malanius.escqrs.escqrsdemo.aggregates.UserAggregate;
import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.projections.UserProjection;
import cz.malanius.escqrs.escqrsdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService users;

    @Autowired
    public UserController(UserService users, UserAggregate userAggregate, UserProjection userProjection) {
        this.users = users;
    }

    @GetMapping
    public Set<UserDTO> listUsers() {
        return users.getAllUsers();
    }

    @PostMapping("/user")
    public UserDTO createUser(@RequestBody UserDTO user) {
        return users.createUser(user);
    }

    @GetMapping("/user/{userId}")
    public UserDTO getUser(@PathVariable UUID userId) {
        return users.getUser(userId);
    }

    @DeleteMapping("/user/{userId}")
    public void deleteUSer(@PathVariable UUID userId) {
        users.deleteUser(userId);
    }

    @PostMapping("/user/{userId}/addresses")
    public UserDTO setUserAddress(@PathVariable UUID userId, @RequestBody Set<AddressDTO> addresses) {
        return users.updateUserAdresses(userId, addresses);
    }

    @PostMapping("/user/{userId}/contacts")
    public UserDTO updateUserContacts(@PathVariable UUID userId, @RequestBody Set<ContactDTO> contacts) {
        return users.updateUserContacts(userId, contacts);
    }

    @GetMapping("/user/{userId}/contacts/{type}")
    public Set<ContactDTO> getUserContactsOfType(@PathVariable UUID userId, @PathVariable String type) {
        return users.getContactsOfType(userId, type);
    }

    @GetMapping("/user/{userId}/addresses/{region}")
    public Set<AddressDTO> getUserAddressesForRegion(@PathVariable UUID userId, @PathVariable String region) {
        return users.getAddressesForRegion(userId, region);
    }
}
