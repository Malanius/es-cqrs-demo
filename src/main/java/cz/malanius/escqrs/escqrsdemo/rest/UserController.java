package cz.malanius.escqrs.escqrsdemo.rest;

import cz.malanius.escqrs.escqrsdemo.model.Address;
import cz.malanius.escqrs.escqrsdemo.model.Contact;
import cz.malanius.escqrs.escqrsdemo.model.User;
import cz.malanius.escqrs.escqrsdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

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
    public User getUser(@PathVariable String id) {
        return users.getUser(id);
    }

    @DeleteMapping("/user/{id}")
    public void deleteUSer(@PathVariable String id) {
        users.deleteUSer(id);
    }

    @PostMapping("/user/{id}/addresses")
    public User updateUserAddress(@PathVariable String id, @RequestBody Set<Address> addresses) {
        return users.updateUserAddresses(id, addresses);
    }

    @PostMapping("/user/{id}/contacts")
    public User updateUserContacts(@PathVariable String id, @RequestBody Set<Contact> contacts) {
        return users.updateUserContacts(id, contacts);
    }
}
