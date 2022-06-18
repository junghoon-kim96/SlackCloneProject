package com.sparta.slackcloneproject.medel;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public User(String s, String s1, String hi) {
        this.username = s;
        this.nickname = hi;
        this.password = s1;
    }
}
