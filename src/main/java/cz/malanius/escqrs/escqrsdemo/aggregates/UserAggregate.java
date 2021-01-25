package cz.malanius.escqrs.escqrsdemo.aggregates;

import cz.malanius.escqrs.escqrsdemo.commands.CreateUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.DeleteUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.UpdateUserCommand;
import cz.malanius.escqrs.escqrsdemo.model.converters.AddressConverter;
import cz.malanius.escqrs.escqrsdemo.model.converters.ContactConverter;
import cz.malanius.escqrs.escqrsdemo.model.converters.UserConverter;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import cz.malanius.escqrs.escqrsdemo.repository.UserWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAggregate {

    private final UserWriteRepository writeRepository;

    @Autowired
    public UserAggregate(UserWriteRepository writeRepository) {
        this.writeRepository = writeRepository;
    }

    public UserDTO handleCreateUserCommand(CreateUserCommand command) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
        UserEntity user = writeRepository.save(userEntity);
        return UserConverter.convert(user);
    }

    public UserDTO handleUpdateUserCommand(UpdateUserCommand command) {
        Optional<UserEntity> maybeUser = writeRepository.findById(command.getUserId());
        return maybeUser.map(u -> {
            if (command.getAddresses() != null)
                u.setAddresses(command.getAddresses().stream().map(AddressConverter::convert).collect(Collectors.toSet()));
            if (command.getContacts() != null)
                u.setContacts(command.getContacts().stream().map(ContactConverter::convert).collect(Collectors.toSet()));
            UserEntity saved = writeRepository.save(u);
            return UserConverter.convert(saved);
        }).orElseThrow();
    }

    public void handleDeleteUserCommand(DeleteUserCommand command) {
        Optional<UserEntity> maybeUser = writeRepository.findById(command.getUserId());
        maybeUser.map(u -> {
            writeRepository.delete(u);
            return Optional.of(u);
        }).orElseThrow();
    }
}
