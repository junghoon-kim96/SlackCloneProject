package com.sparta.slackcloneproject.socket;

import com.sparta.slackcloneproject.dto.ResponseDto;
import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.Message;
import com.sparta.slackcloneproject.repository.ChannelRepository;
import com.sparta.slackcloneproject.repository.InvitedUserChannelRepository;
import com.sparta.slackcloneproject.security.JwtTokenProvider;
import com.sparta.slackcloneproject.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final ChannelRepository channelRepository;
    private final InvitedUserChannelRepository invitedUserChannelRepository;
    private final JwtTokenProvider jwtTokenProvider;

    public MessageDTO addMessage(MessageDTO messageDto, String token, Long channelId) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        Channel channel = validateRole(channelId, userDetails);
        messageRepository.save(Message.builder()
                .message(messageDto.getMessage())
                .user(userDetails.getUser())
                .channel(channel)
                .build());
        messageDto.setUserId(userDetails.getUser().getId());
        messageDto.setUsername(userDetails.getUsername());
        messageDto.setNickname(userDetails.getUser().getNickname());
        messageDto.setIconUrl(userDetails.getUser().getIconUrl());
        return messageDto;
    }

    public ResponseDto<MessageDTO> messages(Long channelId, UserDetailsImpl userDetails) {
        validateRole(channelId, userDetails);
        return new ResponseDto<>(true, messageRepository.findTop100ByChannelIdOrderByCreatedAtDesc(channelId).stream().map(MessageDTO::new).collect(Collectors.toList()));
    }

    private Channel validateRole(Long channelId, UserDetailsImpl userDetails) throws IllegalArgumentException {
        Channel channel = channelRepository.findById(channelId).orElseThrow(() ->
                new IllegalArgumentException("채널이 존재하지 않습니다.")
        );
        if (userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다");
        }
        if(channel.getIsPrivate()) {
            //비공개 채널일떄 초대됐는지 검사
            if (!invitedUserChannelRepository.existsByChannelAndUser(channel, userDetails.getUser())) {
                throw new IllegalArgumentException("채팅 권한이 없습니다.");
            }
        }
        //공개채널일경우 검사 안함
        return channel;
    }
}
