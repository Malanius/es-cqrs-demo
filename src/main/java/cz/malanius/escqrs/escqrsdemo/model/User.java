package cz.malanius.escqrs.escqrsdemo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class User {

    private UUID userId;
    private String firstName;
    private String lastName;
    private Set<Contact> contacts;
    private Set<Address> addresses;

}

