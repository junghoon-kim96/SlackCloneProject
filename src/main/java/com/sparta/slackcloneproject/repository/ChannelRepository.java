package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.medel.Channel;
import com.sparta.slackcloneproject.medel.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel,Long> {

    List<Channel> findAllbyUser(User user);
}
