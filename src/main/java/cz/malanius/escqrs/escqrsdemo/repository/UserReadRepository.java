package cz.malanius.escqrs.escqrsdemo.repository;

import cz.malanius.escqrs.escqrsdemo.projections.UserAddresses;
import cz.malanius.escqrs.escqrsdemo.projections.UserContacts;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// For demo purposes, in-memory only
@Data
@Component
public class UserReadRepository {
    private Map<UUID, UserAddresses> userAddresses = new HashMap<>();
    private Map<UUID, UserContacts> userContacts = new HashMap<>();
}
