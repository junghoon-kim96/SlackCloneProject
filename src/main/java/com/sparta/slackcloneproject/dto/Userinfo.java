package com.sparta.slackcloneproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Userinfo {
    private Long userid;
    private String username;
    private String nickname;
    private String iconUrl;

}
