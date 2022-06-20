package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.medel.Channel;
import com.sparta.slackcloneproject.medel.InvitedUserChannel;
import com.sparta.slackcloneproject.medel.Message;
import com.sparta.slackcloneproject.repository.ChannelRepository;
import com.sparta.slackcloneproject.repository.InvitedUserChannelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final InvitedUserChannelRepository invitedUserChannelRepository;
    public void addMessage(MessageDTO messageDto, UserDetailsImpl userDetails) {

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
        Channel channel = channelRepository.findById(channelId).orElseThrow(()->{
            throw new IllegalArgumentException("채널이 존재하지 않습니다.");
        });
        if(!invitedUserChannelRepository.existsByChannelAndUser(channel,userDetails.getUser())){
            throw new IllegalArgumentException("채팅 권한이 없습니다.");
        }
    }
}
