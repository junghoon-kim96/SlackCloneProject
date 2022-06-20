package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.medel.Channel;
import com.sparta.slackcloneproject.medel.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InvitedUserChannelRepository extends JpaRepository<InvitedUserChannelRepository, Long> {
    boolean existsByChannelAndUser(Channel channel, User user);
}
