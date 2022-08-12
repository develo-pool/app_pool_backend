package appool.pool.project.message;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MessageEditor {

    private final String body;
    private final String messageLink;
    private final String filePath;

    @Builder
    public MessageEditor(String body, String messageLink, String filePath) {
        this.body = body;
        this.messageLink = messageLink;
        this.filePath = filePath;
    }
}
