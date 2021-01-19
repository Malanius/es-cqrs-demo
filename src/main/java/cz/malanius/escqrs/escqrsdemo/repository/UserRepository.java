package cz.malanius.escqrs.escqrsdemo.repository;

import cz.malanius.escqrs.escqrsdemo.model.User;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class UserRepository {
    private final Map<String, User> store = new HashMap<>();

    public User getUSer(String userId) {
        return store.get(userId);
    }

    public void saveUser(User user) {
        store.put(user.getUserid(), user);
    }

    public void removeUser(User user) {
        store.remove(user.getUserid());
    }

    public Set<User> getUsers() {
        return new HashSet<>(store.values());
    }
}
