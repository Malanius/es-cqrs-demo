package cz.malanius.escqrs.escqrsdemo.repository;

import cz.malanius.escqrs.escqrsdemo.model.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserWriteRepository extends CrudRepository<UserEntity, UUID> {

}
