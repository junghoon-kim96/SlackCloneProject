package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChannelRepository extends JpaRepository<Channel,Long> {
    List<Channel> findAllByIsPrivateOrInvitedUserChannelsContains(boolean isPrivate, Long userId);

}
