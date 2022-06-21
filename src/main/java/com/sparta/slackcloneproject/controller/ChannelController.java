package com.sparta.slackcloneproject.controller;

import com.sparta.slackcloneproject.dto.ChannelInviteRequestDto;
import com.sparta.slackcloneproject.dto.ChannelRequestDto;
import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.security.UserDetailsImpl;
import com.sparta.slackcloneproject.service.ChannelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService) {
        this.channelService = channelService;
    }

    //채널 생성
    @PostMapping("/api/channel")
    public ResponseEntity<ResponseDto<?>> createChannel(@RequestBody ChannelRequestDto channelRequestDto,
                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        User user = userDetails.getUser();
        return channelService.createChannel(channelRequestDto, user);
    }

    //초대 할 유저정보 조회
    @GetMapping("/api/userSearch")
    public ResponseEntity<ResponseDto<?>> readUsers(@RequestParam String nickname,
                                                    @RequestParam(required = false) Long channelId,
                                                    @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        return channelService.readUsers(nickname, channelId, userDetails.getUser());
    }


    //채널 목록 조회
    @GetMapping("/api/channelList")
    public ResponseDto<?> readChannels(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        User user = userDetails.getUser();
        return channelService.readChannels(user);
    }

    //채널 삭제(채널 생성자만 가능)
    @DeleteMapping("/api/channel/{channelId}")
    public ResponseEntity<ResponseDto<?>> deleteChannelId(@PathVariable Long channelId,
                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        User user = userDetails.getUser();
        return channelService.deleteChannel(channelId, user);
    }

    //채널 나가기
    @DeleteMapping("/api/channelExit/{channelId}")
    public ResponseEntity<ResponseDto<?>> exitChannel(@PathVariable Long channelId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        User user = userDetails.getUser();
        return channelService.exitChannel(channelId, user);
    }

    //채널 초대
    @PostMapping("/api/channelInvite")
    public ResponseEntity<ResponseDto<?>> inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto) {
        return channelService.inviteChannel(channelInviteRequestDto);
    }
}
