package cz.malanius.escqrs.escqrsdemo.aggregates;

import cz.malanius.escqrs.escqrsdemo.commands.CreateUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.DeleteUserCommand;
import cz.malanius.escqrs.escqrsdemo.commands.UpdateUserCommand;
import cz.malanius.escqrs.escqrsdemo.model.converters.AddressConverter;
import cz.malanius.escqrs.escqrsdemo.model.converters.ContactConverter;
import cz.malanius.escqrs.escqrsdemo.model.converters.UserConverter;
import cz.malanius.escqrs.escqrsdemo.model.dto.UserDTO;
import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import cz.malanius.escqrs.escqrsdemo.projections.UserProjector;
import cz.malanius.escqrs.escqrsdemo.repository.UserWriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserAggregate {

    private final UserWriteRepository writeRepository;
    private final UserProjector userProjector;

    @Autowired
    public UserAggregate(UserWriteRepository writeRepository, UserProjector userProjector) {
        this.writeRepository = writeRepository;
        this.userProjector = userProjector;
    }

    public UserDTO handleCreateUserCommand(CreateUserCommand command) {
        UserEntity userEntity = UserEntity.builder()
                .firstName(command.getFirstName())
                .lastName(command.getLastName())
                .build();
        UserEntity user = writeRepository.save(userEntity);
        UserDTO converted = UserConverter.convert(user);
        userProjector.project(converted);
        return converted;
    }

    public UserDTO handleUpdateUserCommand(UpdateUserCommand command) {
        Optional<UserEntity> maybeUser = writeRepository.findById(command.getUserId());
        return maybeUser.map(u -> {
            if (command.getAddresses() != null)
                u.setAddresses(command.getAddresses().stream().map(AddressConverter::convert).collect(Collectors.toSet()));
            if (command.getContacts() != null)
                u.setContacts(command.getContacts().stream().map(ContactConverter::convert).collect(Collectors.toSet()));
            UserEntity saved = writeRepository.save(u);
            UserDTO converted = UserConverter.convert(saved);
            userProjector.project(converted);
            return converted;
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
