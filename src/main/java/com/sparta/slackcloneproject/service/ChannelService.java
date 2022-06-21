package com.sparta.slackcloneproject.service;

import com.sparta.slackcloneproject.dto.*;
import com.sparta.slackcloneproject.model.Channel;
import com.sparta.slackcloneproject.model.InvitedUserChannel;
import com.sparta.slackcloneproject.model.User;
import com.sparta.slackcloneproject.repository.ChannelRepository;
import com.sparta.slackcloneproject.repository.InvitedUserChannelRepository;
import com.sparta.slackcloneproject.repository.UserRepository;
import com.sun.xml.bind.v2.TODO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


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
    public ResponseEntity<ResponseDto<?>> createChannel(ChannelRequestDto channelRequestDto, User user) {
        List<InvitedUserChannel> invitedUserChannels = new ArrayList<>();

        InvitedUserChannel invitedUserChannelMade = new InvitedUserChannel(user);
        invitedUserChannels.add(invitedUserChannelMade);
        List<Long> userList = channelRequestDto.getUserList();
        for (Long userId : userList) {
            User belongedUser = userRepository.findById(userId).orElse(null);
            if (belongedUser == null) {
                return checkIdAction("아이디");
            }
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(belongedUser);
            invitedUserChannels.add(invitedUserChannel);
        }
        invitedUserChannelRepository.saveAll(invitedUserChannels);
        Channel channel = new Channel(channelRequestDto, invitedUserChannels, user);
        Channel createdChannel = channelRepository.save(channel);

        // return successAction("채널 생성");
        return new ResponseEntity<>(new ResponseDto<>(true, "채널생성", new ChannelResultDto(createdChannel.getId(),createdChannel.getChannelName(),createdChannel.getIsPrivate(),true)),HttpStatus.OK);
    }


    //초대 할 유저정보 조회
    @Transactional(readOnly = true)
    public ResponseDto<UserListDto> readUsers(String nickname, Long channelId, User ownerUser) {
        List<UserListDto> userLists = new ArrayList<>();
        List<User> users = userRepository.findAllByNicknameContaining(nickname);
        for (User user : users) {
            if (channelId != null) {
                //생성되어있는 채널에 유저 초대
                if(!invitedUserChannelRepository.existsByUserAndChannelId(user, channelId)){
                    //이미 초대되어있는 유저가 아닐시
                    UserListDto userListDto = new UserListDto(user.getId(), user.getUsername(), user.getNickname(), user.getIconUrl());
                    userLists.add(userListDto);
                }
            }else {
                //새로운 채널
                if(!Objects.equals(user.getId(), ownerUser.getId())){
                    UserListDto userListDto = new UserListDto(user.getId(), user.getUsername(), user.getNickname(), user.getIconUrl());
                    userLists.add(userListDto);
                }
            }

        }
        return new ResponseDto<>(true, "유저정보 조회 성공", userLists);
    }

    //채널 목록 조회
    @Transactional(readOnly = true)
    public ResponseDto<?> readChannels(User user) {
        // TODO: 2022-06-21  공개채널이면 유저가 속해있지 않아도 뿌려줘야됨
        // List<Channel> channelList = channelRepository.findAllByIsPrivateOrInvitedUserChannelsContains(true, user.getId());
        //
        // System.out.println(channelList);
        List<UserChannelListDto> userChannelList = new ArrayList<>();
//        List<InvitedUserChannel> invitedUserChannels = invitedUserChannelRepository.findAllByUser(user);
        List<InvitedUserChannel> invitedUserChannels = invitedUserChannelRepository.findAllByChannel_IsPrivateOrUser(true, user);
        for (InvitedUserChannel invitedUserChannel : invitedUserChannels) {
            Channel channel = invitedUserChannel.getChannel();

            boolean isOwner = channel.getUser().getId().equals(user.getId());

            UserChannelListDto listDtoIsOwnerFalseDto = new UserChannelListDto(channel.getId(), channel.getChannelName(), channel.getIsPrivate(), isOwner);
            userChannelList.add(listDtoIsOwnerFalseDto);
        }
        return new ResponseDto<>(true, "채널목록 조회 성공", userChannelList);
    }

    //채널 삭제(채널 생성자만 가능)
    @Transactional
    public ResponseEntity<ResponseDto<?>> deleteChannel(Long channelId, User user) { //userchannl entity에 channel은 null인데 user는 살아있다
        Channel channel = channelRepository.findById(channelId).orElse(null);
        if(channel == null){
            return checkIdAction("채널");
        }
        if (!channel.getUser().getId().equals(user.getId())) {
            throw new IllegalArgumentException("채널 생성자가 아닙니다.");
        }
        invitedUserChannelRepository.deleteAll(invitedUserChannelRepository.findAllByChannel(channel));
        channelRepository.delete(channel);
        return successAction("채널 삭제");
    }

    //채널 나가기
    @Transactional
    public ResponseEntity<ResponseDto<?>> exitChannel(Long channelId, User user) {
        Channel channel = channelRepository.findById(channelId).orElse(null);
        if(channel == null){
            return checkIdAction("채널");
        }
        InvitedUserChannel invitedUserChannel = invitedUserChannelRepository.findByUserAndChannel(user, channel);
        invitedUserChannelRepository.delete(invitedUserChannel);
//        for (InvitedUserChannel invitedUserChannel : invitedUserChannels) {
//            if (invitedUserChannel.getUser() == user &&) {
//                invitedUserChannelRepository.delete(invitedUserChannel);
//            }
//        }
        return successAction("채널 나가기");
    }

    //채널 초대
    @Transactional
    public ResponseEntity<ResponseDto<?>> inviteChannel(ChannelInviteRequestDto channelInviteRequestDto) {
        Channel channel = channelRepository.findById(channelInviteRequestDto.getChannelId()).orElse(null);
        if(channel == null){
            return checkIdAction("채널");
        }
        for (Long userId : channelInviteRequestDto.getUserList()) {
            User user = userRepository.findById(userId).orElse(null);
            if(user == null){
                return checkIdAction("유저");
            }
            // 이미 초대된 유저인지 조회
            if (invitedUserChannelRepository.existsByUserAndChannel(user, channel)) {
                throw new IllegalArgumentException("이미 초대된 유저가 포함되어 있습니다.");
            }
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user,channel);
            invitedUserChannelRepository.save(invitedUserChannel);
        }
        return successAction("채널 초대");
    }


    public ResponseEntity<ResponseDto<?>> checkIdAction(String action){
        return new ResponseEntity<>(new ResponseDto<>(false, "존재하지 않는 "+ action +"입니다."), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseDto<?>> successAction(String action){
        return new ResponseEntity<>(new ResponseDto<>(true, action +" 성공!!"), HttpStatus.OK);
    }
}
