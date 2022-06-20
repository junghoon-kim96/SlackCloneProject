package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.InvitedUserChannel;
import com.sparta.slackcloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface InvitedUserChannelRepository extends JpaRepository<InvitedUserChannel, Long> {
    // boolean existsByChannelIdAndUser(Long channelId, User user);
}

