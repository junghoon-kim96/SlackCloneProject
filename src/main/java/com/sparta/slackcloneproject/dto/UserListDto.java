package com.sparta.slackcloneproject.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
@Getter
@NoArgsConstructor
public class UserListDto {
    private Long userId;
    private String username;
    private String nickname;
    private String iconUrl;

    public UserListDto(Long id, String username, String nickname, String iconUrl) {
        this.userId = id;
        this.username = username;
        this.nickname = nickname;
        this.iconUrl = iconUrl;
    }
}
