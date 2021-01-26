package cz.malanius.escqrs.escqrsdemo.store;


import cz.malanius.escqrs.escqrsdemo.events.Event;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

// In-memory for demo purposes is enough
@Service
public class EventStore {

    @Getter
    private final Map<UUID, List<Event>> store = new HashMap<>();

}
