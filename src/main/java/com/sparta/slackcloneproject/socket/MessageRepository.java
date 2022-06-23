package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findTop100ByChannelIdOrderByCreatedAtDesc(Long channelId);

    void deleteByChannel(Channel channel);
}
