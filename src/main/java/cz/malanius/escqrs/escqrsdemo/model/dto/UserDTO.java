package cz.malanius.escqrs.escqrsdemo.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UserDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private Set<ContactDTO> contacts;
    private Set<AddressDTO> addresses;

}

