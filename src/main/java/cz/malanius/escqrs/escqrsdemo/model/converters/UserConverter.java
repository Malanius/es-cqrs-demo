package cz.malanius.escqrs.escqrsdemo.model.converters;

import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserConverter {

    public static UserDTO convert(UserEntity entity) {
        UserDTO.UserDTOBuilder builder = UserDTO.builder()
                .id(entity.getUserId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName());

        if (entity.getAddresses() != null) {
            builder.addresses(entity.getAddresses().stream()
                    .map(AddressConverter::convert).collect(Collectors.toSet()));
        }

        if (entity.getContacts() != null) {
            builder.contacts(entity.getContacts().stream()
                    .map(ContactConverter::convert).collect(Collectors.toSet()));
        }
        return builder.build();
    }
}
