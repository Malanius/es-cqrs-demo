package cz.malanius.escqrs.escqrsdemo.queries;

import lombok.Data;

import java.util.UUID;

@Data
public class AddressByRegionQuery {
    private UUID userId;
    private String state;
}
