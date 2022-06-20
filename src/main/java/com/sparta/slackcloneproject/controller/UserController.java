package com.sparta.slackcloneproject.controller;


import com.sparta.slackcloneproject.dto.LoginRequestDto;
import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.dto.SignUpRequestDto;
import com.sparta.slackcloneproject.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/signup")
    public ResponseEntity<ResponseDto<?>> signup (@Valid @RequestBody SignUpRequestDto requestDto) {

        return ResponseEntity.ok().body(userService.signup(requestDto));
    }

    @PostMapping("/api/login")
    public ResponseEntity<ResponseDto<?>> login (@RequestBody LoginRequestDto requestDto) {
        return ResponseEntity.ok().body(userService.login(requestDto));
    }
}
