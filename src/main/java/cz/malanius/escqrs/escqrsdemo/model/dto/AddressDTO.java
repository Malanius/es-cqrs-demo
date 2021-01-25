package cz.malanius.escqrs.escqrsdemo.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {

    private String city;
    private String state;
    private String postcode;

}
