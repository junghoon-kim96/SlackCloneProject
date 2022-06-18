package com.sparta.slackcloneproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChannelListResponseDto<T> {
    private boolean response;
    private String message;
    private List<UserChannelListDto> list;

    public ChannelListResponseDto(List<UserChannelListDto> userChannelList) {
        this.list = userChannelList;
    }
}