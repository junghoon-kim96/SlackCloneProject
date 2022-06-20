package com.sparta.slackcloneproject.socket;


import com.sparta.slackcloneproject.model.Message;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageDTO {

    private Long channelId;
    private String message;
    private long createdAt;
    private Long userId;
    private String username;
    private String nickname;
    private String iconUrl;

    public MessageDTO(Message message) {
        this.channelId = message.getChannel().getId();
        this.message = message.getMessage();
        this.createdAt = message.getCreatedAt();
        this.userId = message.getUser().getId();
        this.username = message.getUser().getUsername();
        this.nickname = message.getUser().getNickname();
        this.iconUrl = message.getUser().getIconUrl();
    }
}