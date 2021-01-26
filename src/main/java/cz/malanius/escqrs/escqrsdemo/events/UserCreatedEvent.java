package cz.malanius.escqrs.escqrsdemo.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserCreatedEvent extends Event {
    private UUID userId;
    private String firstName;
    private String lastName;
}
