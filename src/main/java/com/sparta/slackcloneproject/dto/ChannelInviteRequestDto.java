package com.sparta.slackcloneproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@Getter
public class ChannelInviteRequestDto {
    private Long channelId;
    private List<Long> userId;
}
