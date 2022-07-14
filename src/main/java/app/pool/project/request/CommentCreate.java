package app.pool.project.request;

import lombok.Data;

@Data
public class CommentCreate {
    private String body;

    private Long messageId;
}
