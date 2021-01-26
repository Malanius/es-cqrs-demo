package cz.malanius.escqrs.escqrsdemo.queries;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AddressByRegionQuery {
    private UUID userId;
    private String state;
}
