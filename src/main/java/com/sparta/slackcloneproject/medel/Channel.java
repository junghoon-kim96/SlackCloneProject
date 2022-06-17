package com.sparta.slackcloneproject.medel;

import javax.persistence.*;
import java.util.List;

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

}
