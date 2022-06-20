package com.sparta.slackcloneproject.model;

import com.sparta.slackcloneproject.dto.ChannelRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity
public class Channel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false)
    private String channelName;

    @Column(nullable = true)
    private String description;

    @Column(nullable = false)
    private boolean isPrivate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user; // 소유자

    @OneToMany
    @JoinColumn(name = "channel_id")
    private List<InvitedUserChannel> invitedUserChannels; //초대된 유저

    public Channel(ChannelRequestDto channelRequestDto, List<InvitedUserChannel> invitedUserChannels, User user) {
        this.channelName = channelRequestDto.getChannelName();
        this.description = channelRequestDto.getDescription();
        this.isPrivate = channelRequestDto.isPrivate();
        this.user = user;
        this.invitedUserChannels = invitedUserChannels;
    }
    public void update(List<InvitedUserChannel> invitedUserChannels){
        this.invitedUserChannels = invitedUserChannels;
    }
}
