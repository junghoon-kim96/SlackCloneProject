package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);

    Optional<User> findByUsername(String username);
}
