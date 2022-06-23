package com.sparta.slackcloneproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.slackcloneproject.model.Channel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean response;
    private String message;
    private List<T> list;
    private UserListDto userinfo;
    private ChannelResultDto result;
    private Long userId;
    private String username;
    private String nickname;
    private String iconUrl;
    public ResponseDto (boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    public ResponseDto(boolean response, UserListDto userinfo, String message) {
        this.response = response;
        this.userinfo = userinfo;
        this.message = message;
    }

    public ResponseDto(boolean response, String message,List<T> userChannelList) {
        this.response = response;
        this.message = message;
        this.list = userChannelList;
    }
    public ResponseDto(boolean response,List<T> list) {
        this.response = response;
        this.list = list;
    }


    public ResponseDto(boolean response, Long userId, String username, String nickname, String iconUrl) {
        this.response = response;
        this.userId = userId;
        this.username = username;
        this.nickname = nickname;
        this.iconUrl = iconUrl;
    }

    public ResponseDto(boolean response, String message, ChannelResultDto channelResultDto) {
        this.response = response;
        this.message = message;
        this.result = channelResultDto;
    }
}