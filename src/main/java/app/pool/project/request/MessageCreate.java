package app.pool.project.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageCreate {


    private String title;
    private String body;
    private String messageLink;

    @Builder

    public MessageCreate(String title, String body, String messageLink) {
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
    }
}
