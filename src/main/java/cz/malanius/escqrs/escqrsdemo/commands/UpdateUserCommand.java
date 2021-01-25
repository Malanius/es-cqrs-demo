package cz.malanius.escqrs.escqrsdemo.commands;

import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import lombok.Builder;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class UpdateUserCommand {

    private UUID userId;
    private Set<AddressDTO> addresses;
    private Set<ContactDTO> contacts;

}
