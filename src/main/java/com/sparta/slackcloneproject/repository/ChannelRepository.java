package com.sparta.slackcloneproject.repository;

import com.sparta.slackcloneproject.medel.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

}
