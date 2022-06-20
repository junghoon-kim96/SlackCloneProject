package com.sparta.slackcloneproject.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Userinfo {
    private Long userid;
    private String nickname;
    private String icon_url;

}
