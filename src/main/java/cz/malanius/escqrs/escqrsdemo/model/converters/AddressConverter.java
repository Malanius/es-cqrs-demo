package cz.malanius.escqrs.escqrsdemo.model.converters;

import cz.malanius.escqrs.escqrsdemo.model.dto.AddressDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.AddressEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressConverter {

    public static AddressDTO convert(AddressEntity entity) {
        return AddressDTO.builder()
                .id(entity.getId())
                .city(entity.getCity())
                .postcode(entity.getPostcode())
                .state(entity.getState())
                .build();
    }

    public static AddressEntity convert(AddressDTO dto) {
        return AddressEntity.builder()
                .id(dto.getId())
                .city(dto.getCity())
                .postcode(dto.getPostcode())
                .state(dto.getState())
                .build();
    }
}
