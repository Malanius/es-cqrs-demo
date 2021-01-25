package cz.malanius.escqrs.escqrsdemo.commands;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class DeleteUserCommand {

    UUID userId;

}
