package com.sparta.slackcloneproject.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.sparta.slackcloneproject.socket.MessageDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto<T> {
    private boolean response;
    private String message;
    private List<T> list;

    public ResponseDto(List<T> messages) {
        this.response = true;
        this.list = messages;
    }
}