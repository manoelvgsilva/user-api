package com.app.user.repository;

import com.app.user.entity.UserRedis;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * userredisrepository.
 */
@Repository
public interface UserRedisRepository extends CrudRepository<UserRedis, ObjectId> {
  Optional<UserRedis> findByEmail(String email);

  Optional<UserRedis> findByCpf(String cpf);
}