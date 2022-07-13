package app.pool.project.response;

import app.pool.project.domain.Message;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageResponse {

    private final Long id;
    private final String title;
    private final String body;
//    private PoolUser user;
    private final String messageLink;
//    private final List<String> messageImg;


    public MessageResponse(Message message) {
        this.id = message.getId();
        this.title = message.getTitle();
        this.body = message.getBody();
        this.messageLink = message.getMessageLink();
    }

    @Builder
    public MessageResponse(Long id, String title, String body, String messageLink) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
    }
}