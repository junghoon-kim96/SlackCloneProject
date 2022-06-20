package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.InvitedUserChannel;
import com.sparta.slackcloneproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitedUserChannelRepository extends JpaRepository<InvitedUserChannel, Long> {
    List<InvitedUserChannel> findAllByChannel(Channel channel);
    boolean existsByChannelAndUser(Channel channel, User user);

    List<InvitedUserChannel> findAllByUserId(Long userId);

    List <InvitedUserChannel> findAllByUser(User user);

    InvitedUserChannel findByUserAndChannel(User user, Channel channel);

}

