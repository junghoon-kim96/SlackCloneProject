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
                          InvitedUserChannelRepository invitedUserChannelRepository){
        this.channelRepository = channelRepository;
        this.userRepository = userRepository;
        this.invitedUserChannelRepository = invitedUserChannelRepository;
    }

//    @Transactional
//    public void createChannel(ChannelRequestDto channelRequestDto) {
//        List<InvitedUserChannel> invitedUserChannels = new ArrayList<>();
//
//        List<Long> userList = channelRequestDto.getUserList();
//        for(Long userId : userList){
//           User user = userRepository.findById(userId)
//                   .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
//            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user);
//            invitedUserChannels.add(invitedUserChannel);
//        }
//
//        invitedUserChannelRepository.saveAll(invitedUserChannels);
//        Channel channel = new Channel(channelRequestDto, invitedUserChannels);
//        channelRepository.save(channel);
//    }




    @Transactional
    public void createChannel(ChannelRequestDto channelRequestDto) {
        List<InvitedUserChannel> invitedUserChannels = new ArrayList<>();

        List<Long> userList = channelRequestDto.getUserList();
        for(Long userId : userList){

            User user = userRepository.save( new User("123@naver.com","1234","hi"));
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user);
            invitedUserChannels.add(invitedUserChannel);
        }

        invitedUserChannelRepository.saveAll(invitedUserChannels);
        User user2 = userRepository.save(new User("1243@naver.com","12345","hia"));
        Channel channel = new Channel(channelRequestDto, invitedUserChannels,user2);
        channelRepository.save(channel);
    }

    public UserListResponseDto readUsers(String nickname) {
        List<UserListDto> userLists = new ArrayList<>();
        List<User> users = userRepository.findAllByNicknameContaining(nickname);
        for(User user : users){
            UserListDto userListDto = new UserListDto(user.getId(), user.getUsername(), user.getNickname(),user.getIconUrl());
            userLists.add(userListDto);
        }
        return UserListResponseDto userListResponseDto = new UserListResponseDto(userLists);

    }

    public ResponseDto<?> readChannels() {//받아온 유저로 채널 꺼내온다
        List<UserChannelListDto> userChannelList = new ArrayList<>();
        List<Channel> channels = channelRepository.findAllbyUser(user);
        for(Channel channel : channels){
            if(channel.getUser() == user){
                Boolean isOwner = true;
                UserChannelListDto listDtoIsOwnerTrueDto = new UserChannelListDto(channel.getId(),channel.getChannelName(),channel.isPrivate(),isOwner);
                userChannelList.add(listDtoIsOwnerTrueDto);
            }
            Boolean isOwner = false;
            UserChannelListDto listDtoIsOwnerFalseDto = new UserChannelListDto(channel.getId(),channel.getChannelName(),channel.isPrivate(),isOwner);
            userChannelList.add(listDtoIsOwnerFalseDto);
        }
        return new ResponseDto<>(userChannelList);


    }

    public void deleteChannel(Long channelId) { //유저 받아와서 비교
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));

        if(channel.getUser() != channel.getUser() ){
            throw new IllegalArgumentException("채널 생성자가 아닙니다.");
        }
        channelRepository.delete(channel);

    }

    @Transactional
    public void exitChannel(Long channelId) {//유저를 받아아와서 속한 채널 지우기(그 유저가 생성한 채널은 지우지 않기)
        List<InvitedUserChannel> invitedUserChannels = invitedUserChannelRepository.findAllById(User.getId);

        for(InvitedUserChannel invitedUserChannel : invitedUserChannels){
            if(invitedUserChannel.getUser() == user){
                invitedUserChannelRepository.delete(invitedUserChannel);
            }
        }

    }

    @Transactional
    public void inviteChannel(ChannelInviteRequestDto channelInviteRequestDto) {
        Channel channel = channelRepository.findById(channelInviteRequestDto.getChannelId())
                .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
        for(Long userId : channelInviteRequestDto.getUserId()){
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new NullPointerException("해당 아이디가 존재하지 않습니다."));
            InvitedUserChannel invitedUserChannel = new InvitedUserChannel(user);
            invitedUserChannelRepository.save(invitedUserChannel);
        }
//        channel.update(invitedUserChannelRepository.findAllByChannel(channel));


    }


}
