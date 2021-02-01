package cz.malanius.escqrs.escqrsdemo.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;
import java.util.UUID;

@Data
@EqualsAndHashCode(of = "id")
public abstract class Event {
    protected final UUID id = UUID.randomUUID();
    protected final Date created = new Date();
}
