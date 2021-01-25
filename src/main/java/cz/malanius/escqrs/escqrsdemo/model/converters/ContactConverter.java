package cz.malanius.escqrs.escqrsdemo.model.converters;

import cz.malanius.escqrs.escqrsdemo.model.dto.ContactDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.ContactEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ContactConverter {

    public static ContactDTO convert(ContactEntity entity) {
        return ContactDTO.builder()
                .type(entity.getType())
                .detail(entity.getDetail())
                .build();
    }
}
