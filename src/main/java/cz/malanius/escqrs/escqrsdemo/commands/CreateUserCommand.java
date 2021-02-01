package cz.malanius.escqrs.escqrsdemo.commands;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CreateUserCommand {

    private UUID userId;
    private String firstName;
    private String lastName;

}
