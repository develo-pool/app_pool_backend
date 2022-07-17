package app.pool.project.message.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MessageEdit {

    private String title;

    private String body;

    private String messageLink;

    @Builder
    public MessageEdit(String title, String body, String messageLink) {
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
    }
}
