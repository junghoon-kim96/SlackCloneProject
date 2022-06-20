package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class MessageController {

    private final SimpMessagingTemplate template;
    private final MessageService messageService;
    @ResponseBody
    @GetMapping("/api/chat/{channelId}")
    public ResponseEntity messages(@PathVariable Long channelId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        return ResponseEntity.ok().body(messageService.messages(channelId, userDetails));
    }

    // //stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
    // @MessageMapping(value = "/enter")
    // public void enter(MessageDTO messageDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
    //     messageDto.setMessage(userDetails.getUser().getUsername + "님이 채팅방에 참여하였습니다.");
    //     template.convertAndSend("/sub/channel/" + messageDto.getChannelId(), messageDto);
    // }

    @MessageMapping(value = "/message/{channelId}")
    public void addMessage(MessageDTO messageDto,@DestinationVariable Long channerId, @AuthenticationPrincipal UserDetailsImpl userDetails){
        messageService.addMessage(messageDto,userDetails);
        template.convertAndSend("/sub/channel/" + channerId, messageDto);
    }
}