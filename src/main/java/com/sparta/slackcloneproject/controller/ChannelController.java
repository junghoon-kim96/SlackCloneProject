package com.sparta.slackcloneproject.controller;

import com.sparta.slackcloneproject.dto.ChannelInviteRequestDto;
import com.sparta.slackcloneproject.dto.ChannelRequestDto;
import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.dto.UserListResponseDto;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.security.UserDetailsImpl;
import com.sparta.slackcloneproject.service.ChannelService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    //채널 생성
    @PostMapping("/api/channel")
    public void createChannel(@RequestBody ChannelRequestDto channelRequestDto,
                              @AuthenticationPrincipal UserDetailsImpl userDetails){
        User user = userDetails.getUser();
        channelService.createChannel(channelRequestDto,user);
    }

    //초대 할 유저정보 조회
   @GetMapping("/api/userSearch?{검색내용}")
    public UserListResponseDto readUsers(@RequestParam String nickname){
       return channelService.readUsers(nickname);
    }


    //채널 목록 조회
    @GetMapping("/api/channelList")
    public ResponseDto<?> readChannels(@AuthenticationPrincipal UserDetailsImpl userDetails){//유저 받아와야함
        User user = userDetails.getUser();
        return channelService.readChannels(user);
    }

    //채널 삭제(채널 생성자만 가능)
    @DeleteMapping("/api/channel/{channelId}")
    public void  deleteChannelId(@PathVariable Long channelId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails){// 유저를 받아온다, 그리고 비교
        User user = userDetails.getUser();
        channelService.deleteChannel(channelId,user);
    }

    @DeleteMapping("/api/channelExit/{channelId}")
    public void exitChannel(@PathVariable Long channelId,
                            @AuthenticationPrincipal UserDetailsImpl userDetails){// 유저를 받아온다
        User user = userDetails.getUser();
        channelService.exitChannel(channelId,user);
    }

    @PostMapping("/api/channelInvite")
    public void inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto){
        channelService.inviteChannel(channelInviteRequestDto);
    }
}
