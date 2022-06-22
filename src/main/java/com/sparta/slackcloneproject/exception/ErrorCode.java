package com.sparta.slackcloneproject.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public enum ErrorCode {
    // 400 Bad Request
    NULL_CHANNEL_NAME(HttpStatus.BAD_REQUEST, "400_1", "채널 이름을 입력해 주세요"),
    NULL_CHANNEL_DESCRIPTION(HttpStatus.BAD_REQUEST, "400_2", "채널 설명란을 입력해 주세요 ");


    private final HttpStatus httpStatus;
    private final String errorCode;
    private final String errorMessage;

    ErrorCode(HttpStatus httpStatus, String errorCode, String errorMessage) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}
