package app.pool.project.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageEditor {

    private final String title;
    private final String body;
    private final String messageLink;

    @Builder
    public MessageEditor(String title, String body, String messageLink) {
        this.title = title;
        this.body = body;
        this.messageLink = messageLink;
    }
}
