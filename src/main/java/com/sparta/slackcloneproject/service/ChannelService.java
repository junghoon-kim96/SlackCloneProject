package com.sparta.slackcloneproject.service;

import com.sparta.slackcloneproject.dto.*;
import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.InvitedUserChannel;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.repository.ChannelRepository;
import com.sparta.slackcloneproject.repository.InvitedUserChannelRepository;
import com.sparta.slackcloneproject.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChannelService {

    private final ChannelRepository channelRepository;
    private final UserRepository userRepository;
    private final InvitedUserChannelRepository invitedUserChannelRepository;

    public ChannelService(ChannelRepository channelRepository,
                          UserRepository userRepository,
                          InvitedUserChannelRepository invitedUserChannelRepository) {
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.invitedUserChannelRepository = invitedUserChannelRepository;
    }

    //채널 생성
    @Transactional
    public ResponseDto<?> createChannel(ChannelRequestDto channelRequestDto, User user) {
        List<InvitedUserChannel> invitedUserChannels = new ArrayList<>();

        InvitedUserChannel invitedUserChannelMade = new InvitedUserChannel(user);
        invitedUserChannels.add(invitedUserChannelMade);
        List<Long> userList = channelRequestDto.getUserList();
        for (Long userId : userList) {
            User belongedUser = userRepository.findById(userId)
                    .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(belongedUser);
            invitedUserChannels.add(invitedUserChannel);
        }

        invitedUserChannelRepository.saveAll(invitedUserChannels);
        Channel channel = new Channel(channelRequestDto, invitedUserChannels, user);
        channelRepository.save(channel);

        return new ResponseDto<>();
    }

//    @Transactional
//    public void createChannel(ChannelRequestDto channelRequestDto) {
//        List<InvitedUserChannel> invitedUserChannels = new ArrayList<>();
//
//        List<Long> userList = channelRequestDto.getUserList();
//        for(Long userId : userList){
//
//            User user = userRepository.save( new User("123@naver.com","1234","hi"));
//            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user);
//            invitedUserChannels.add(invitedUserChannel);
//        }
//
//        invitedUserChannelRepository.saveAll(invitedUserChannels);
//        User user2 = userRepository.save(new User("1243@naver.com","12345","hia"));
//        Channel channel = new Channel(channelRequestDto, invitedUserChannels,user2);
//        channelRepository.save(channel);
//    }

    //초대 할 유저정보 조회
    public ResponseDto<?> readUsers(String nickname) {
        List<UserListDto> userLists = new ArrayList<>();
        List<User> users = userRepository.findAllByNicknameContaining(nickname);
        for (User user : users) {
            UserListDto userListDto = new UserListDto(user.getId(), user.getUsername(), user.getNickname(), user.getIconUrl());
            userLists.add(userListDto);
        }
        return new ResponseDto<>(true, "성공", userLists);

    }

    //채널 목록 조회
    public ResponseDto<?> readChannels(User user) {
        List<UserChannelListDto> userChannelList = new ArrayList<>();
        List<InvitedUserChannel> invitedUserChannels = invitedUserChannelRepository.findAllByUser(user);
        for (InvitedUserChannel invitedUserChannel : invitedUserChannels) {
            Channel channel = invitedUserChannel.getChannel();
            if (channel.getUser() == user) {
                Boolean isOwner = true;
                UserChannelListDto listDtoIsOwnerTrueDto = new UserChannelListDto(channel.getId(), channel.getChannelName(), channel.isPrivate(), isOwner);
                userChannelList.add(listDtoIsOwnerTrueDto);
            }
            Boolean isOwner = false;
            UserChannelListDto listDtoIsOwnerFalseDto = new UserChannelListDto(channel.getId(), channel.getChannelName(), channel.isPrivate(), isOwner);
            userChannelList.add(listDtoIsOwnerFalseDto);
        }
        return new ResponseDto<>(true, "성공", userChannelList);
    }

    //채널 삭제(채널 생성자만 가능)
    public ResponseDto<?> deleteChannel(Long channelId, User user) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        if (channel.getUser() != user) {
            throw new IllegalArgumentException("채널 생성자가 아닙니다.");
        }
        channelRepository.delete(channel);
        return new ResponseDto<>(true, "성공");
    }

    //채널 나가기
    @Transactional
    public ResponseDto<?> exitChannel(Long channelId, User user) {
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        InvitedUserChannel invitedUserChannel = invitedUserChannelRepository.findByUserAndChannel(user, channel);
        invitedUserChannelRepository.delete(invitedUserChannel);
//        for (InvitedUserChannel invitedUserChannel : invitedUserChannels) {
//            if (invitedUserChannel.getUser() == user &&) {
//                invitedUserChannelRepository.delete(invitedUserChannel);
//            }
//        }
        return new ResponseDto<>(true, "성공");
    }

    //채널 초대
    @Transactional
    public ResponseDto<?> inviteChannel(ChannelInviteRequestDto channelInviteRequestDto) {
        Channel channel = channelRepository.findById(channelInviteRequestDto.getChannelId())
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        for (Long userId : channelInviteRequestDto.getUserId()) {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user);
            invitedUserChannelRepository.save(invitedUserChannel);
        }
        channel.update(invitedUserChannelRepository.findAllByChannel(channel));
        return new ResponseDto<>(true, "성공");
    }
}
