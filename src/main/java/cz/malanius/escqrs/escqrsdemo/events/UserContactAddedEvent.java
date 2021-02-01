package cz.malanius.escqrs.escqrsdemo.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserContactAddedEvent extends Event {
    private String contactType;
    private String contactDetails;
}
