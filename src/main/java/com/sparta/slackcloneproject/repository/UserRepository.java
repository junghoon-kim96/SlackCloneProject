package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.dto.UserListDto;
import com.sparta.slackcloneproject.medel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long> {
    List<User> findAllByNicknameContaining(String nickName);
}
