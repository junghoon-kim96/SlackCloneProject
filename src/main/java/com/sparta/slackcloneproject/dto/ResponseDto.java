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
public class ResponseDto<T> {
    private boolean response;
    private String message;
    private List<T> list;
    private Userinfo userinfo;

    public ResponseDto (boolean response, String message) {
        this.response = response;
        this.message = message;
    }

    public ResponseDto(boolean reponse, Userinfo userinfo, String message) {
        this.response = reponse;
        this.userinfo = userinfo;
        this.message = message;
    }
}