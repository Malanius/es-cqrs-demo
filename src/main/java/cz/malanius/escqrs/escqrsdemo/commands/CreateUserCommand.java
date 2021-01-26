package cz.malanius.escqrs.escqrsdemo.commands;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateUserCommand {

    private String firstName;
    private String lastName;

}
