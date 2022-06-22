package com.sparta.slackcloneproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class UserChannelListDto {
    private Long channelId;
    private String channelName;
    private Boolean isPrivate;
    private Boolean isOwner;
    private String description;

    public UserChannelListDto(Long id, String channelName, Boolean isPrivate, Boolean isOwner, String description) {
        this.channelId = id;
        this.channelName = channelName;
        this.isPrivate = isPrivate;
        this.isOwner = isOwner;
        this.description = description;
    }
}
