package cz.malanius.escqrs.escqrsdemo.queries;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ContactByTypeQuery {
    private UUID userId;
    private String contactType;
}
