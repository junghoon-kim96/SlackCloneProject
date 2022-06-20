package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.Message;
import com.sparta.slackcloneproject.repository.ChannelRepository;
import com.sparta.slackcloneproject.repository.InvitedUserChannelRepository;
import com.sparta.slackcloneproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final InvitedUserChannelRepository invitedUserChannelRepository;
    public void addMessage(MessageDTO messageDto, UserDetailsImpl userDetails) {
        Channel channel = channelRepository.findById(messageDto.getChannelId()).orElseThrow(()-> new IllegalArgumentException("채널 아이디가 없습니다."));
        validateRole(messageDto.getChannelId(), userDetails);
        messageRepository.save(Message.builder()
                        .message(messageDto.getMessage())
                        .user(userDetails.getUser())
                        .channel(channel)
                        .build());

    }

    public ResponseDto<MessageDTO> messages(Long channelId, UserDetailsImpl userDetails) {
        validateRole(channelId, userDetails);
        return new ResponseDto<>(messageRepository.findTop100ByChannelIdOrderByCreatedAtAsc(channelId).stream().map(MessageDTO::new).collect(Collectors.toList()));
    }

    private void validateRole(Long channelId, UserDetailsImpl userDetails) throws IllegalArgumentException{
        Channel channel = channelRepository.findById(channelId).orElseThrow(()->
             new IllegalArgumentException("채널이 존재하지 않습니다.")
        );
        if(!invitedUserChannelRepository.existsByChannelAndUser(channel,userDetails.getUser())){
            throw new IllegalArgumentException("채팅 권한이 없습니다.");
        }
    }
}
