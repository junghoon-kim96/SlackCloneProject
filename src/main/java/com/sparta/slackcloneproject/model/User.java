package com.sparta.slackcloneproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@NoArgsConstructor
@Getter
@Entity(name = "users")
public class User {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = true)
    private String iconUrl;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<InvitedUserChannel> invitedUserChannels;

    public User (SignUpRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.nickname = requestDto.getNickname();
        this.iconUrl = requestDto.getIconUrl();
        this.password = requestDto.getPassword();
    }

}
