package com.sparta.slackcloneproject.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;



@Getter
@Setter
public class RestApiException {
    private Boolean response;
    private String Message;
}
