package cz.malanius.escqrs.escqrsdemo.model.converters;

import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.ContactEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactConverter {

    public static ContactDTO convert(ContactEntity entity) {
        return ContactDTO.builder()
                .id(entity.getId())
                .type(entity.getType())
                .detail(entity.getDetail())
                .build();
    }

    public static ContactEntity convert(ContactDTO dto) {
        return ContactEntity.builder()
                .id(dto.getId())
                .type(dto.getType())
                .detail(dto.getDetail())
                .build();
    }
}
