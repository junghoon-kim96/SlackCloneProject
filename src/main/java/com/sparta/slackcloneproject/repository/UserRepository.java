package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository <User, Long> {
    boolean existsByUsername(String username);
    boolean existsByNickname(String nickname);
}
