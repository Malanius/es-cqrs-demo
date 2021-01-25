package cz.malanius.escqrs.escqrsdemo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactDTO {

    private String type;
    private String detail;

}
