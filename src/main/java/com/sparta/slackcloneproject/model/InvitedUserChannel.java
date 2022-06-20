package com.sparta.slackcloneproject.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class InvitedUserChannel {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "channel_id")
    private Channel channel;

    public InvitedUserChannel(User user){
        this.user = user;
    }

}
