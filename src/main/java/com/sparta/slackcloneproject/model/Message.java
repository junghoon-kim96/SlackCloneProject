package com.sparta.slackcloneproject.model;

import javax.persistence.*;

@Entity
public class Message extends Timestamped {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Channel channel;
    @Column
    private String message;

}
