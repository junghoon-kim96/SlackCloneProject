package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.medel.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {
    List<Message> findTop100ByChannelIdOrderByCreatedAtAsc(Long channelId);
}
