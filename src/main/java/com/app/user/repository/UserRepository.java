package com.app.user.repository;

import com.app.user.entity.User;
import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * userrepository.
 */
@Repository
public interface UserRepository extends MongoRepository<User, Long> {
  Optional<User> findByUsername(String cpf);
}