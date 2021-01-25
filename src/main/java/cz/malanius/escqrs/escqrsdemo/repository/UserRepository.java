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

    public User getUser(String userId) {
        return store.get(userId);
    }

    public User saveUser(User user) {
        store.put(user.getUserId().toString(), user);
        return user;
    }

    public void removeUser(String userId) {
        store.remove(userId);
    }

    public Set<User> getUsers() {
        return new HashSet<>(store.values());
    }
}
