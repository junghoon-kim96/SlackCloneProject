package com.sparta.slackcloneproject.controller;

import com.sparta.slackcloneproject.dto.ChannelInviteRequestDto;
import com.sparta.slackcloneproject.dto.ChannelRequestDto;
import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.dto.UserListResponseDto;
import com.sparta.slackcloneproject.service.ChannelService;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChannelController {

    private final ChannelService channelService;

    public ChannelController(ChannelService channelService){
        this.channelService = channelService;
    }

    //채널 생성
    @PostMapping("/api/channel")
    public void createChannel(@RequestBody ChannelRequestDto channelRequestDto){
        channelService.createChannel(channelRequestDto);
    }

    // 유저 조회
   @GetMapping("/api/userSearch?{검색내용}")
    public UserListResponseDto readUsers(@RequestParam String nickname){
       return channelService.readUsers(nickname);
    }


    //채널 목록 조회
    @GetMapping("/api/channelList")
    public ResponseDto<?> readChannels(){//유저 받아와야함
       return channelService.readChannels();
    }

    //채널 삭제(채널 생성자만 가능)
    @DeleteMapping("/api/channel/{channelId}")
    public void  deleteChannelId(@PathVariable Long channelId){// 유저를 받아온다, 그리고 비교
        channelService.deleteChannel(channelId);
    }

    @DeleteMapping("/api/channelExit/{channelId}")
    public void exitChannel(@PathVariable Long channelId){// 유저를 받아온다
        channelService.exitChannel(channelId);
    }

    @PostMapping("/api/channelInvite")
    public void inviteChannel(@RequestBody ChannelInviteRequestDto channelInviteRequestDto){
        channelService.inviteChannel(channelInviteRequestDto);
    }
}
