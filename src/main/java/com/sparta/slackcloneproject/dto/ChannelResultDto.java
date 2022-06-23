package com.sparta.slackcloneproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChannelResultDto {
    private Long channelId;
    private String channelName;
    private Boolean isPrivate;
    private Boolean isOwner;

    public ChannelResultDto(Long channelId, String channelName, Boolean isPrivate, boolean isOwner) {
        this.channelId = channelId;
        this.channelName = channelName;
        this.isPrivate = isPrivate;
        this.isOwner = isOwner;
    }
}
