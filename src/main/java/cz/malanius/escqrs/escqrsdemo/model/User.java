package cz.malanius.escqrs.escqrsdemo.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class User {

    private String userid;
    private String firstName;
    private String lastName;
    private Set<Contact> contacts;
    private Set<Address> addresses;

}

