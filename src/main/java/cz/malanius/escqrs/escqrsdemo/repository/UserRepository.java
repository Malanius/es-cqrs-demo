package cz.malanius.escqrs.escqrsdemo.repository;

import cz.malanius.escqrs.escqrsdemo.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

}
