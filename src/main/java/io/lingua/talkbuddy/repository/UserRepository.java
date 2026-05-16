package io.lingua.talkbuddy.repository;

import io.lingua.talkbuddy.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
