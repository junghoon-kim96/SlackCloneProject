package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.medel.Channel;
import com.sparta.slackcloneproject.medel.InvitedUserChannel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvitedUserChannelRepository extends JpaRepository<InvitedUserChannel, Long> {
    List<InvitedUserChannel> findAllByChannel(Channel channel);
}
