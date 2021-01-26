package cz.malanius.escqrs.escqrsdemo.store;


import cz.malanius.escqrs.escqrsdemo.events.Event;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.*;

// In-memory for demo purposes is enough
@Service
public class EventStore {

    @Getter
    private final Map<UUID, List<Event>> store = new HashMap<>();

    public void addEvent(UUID userId, Event event) {
        List<Event> events = Optional.ofNullable(store.get(userId)).orElse(new ArrayList<>());
        events.add(event);
        store.put(userId, events);
    }

    public List<Event> getEventsForUser(UUID userId) {
        return List.copyOf(store.get(userId));
    }
}
