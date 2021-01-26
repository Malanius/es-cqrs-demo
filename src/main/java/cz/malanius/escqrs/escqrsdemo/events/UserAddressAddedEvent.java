package cz.malanius.escqrs.escqrsdemo.events;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class UserAddressAddedEvent extends Event {
    private String city;
    private String state;
    private String postCode;
}
