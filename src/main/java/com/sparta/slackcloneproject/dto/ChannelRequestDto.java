package com.sparta.slackcloneproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelRequestDto {
    private String channelName;
    private String description;
    private boolean isPrivate;
    private List<Long> userList;
}