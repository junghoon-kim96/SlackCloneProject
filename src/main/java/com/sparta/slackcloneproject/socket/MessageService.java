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
                new IllegalArgumentException("????????? ???????????? ????????????.")
        );
        if (userDetails == null) {
            throw new IllegalArgumentException("???????????? ???????????????");
        }
        if(channel.getIsPrivate()) {
            //????????? ???????????? ??????????????? ??????
            if (!invitedUserChannelRepository.existsByChannelAndUser(channel, userDetails.getUser())) {
                throw new IllegalArgumentException("?????? ????????? ????????????.");
            }
        }
        //????????????????????? ?????? ??????
        return channel;
    }
}
