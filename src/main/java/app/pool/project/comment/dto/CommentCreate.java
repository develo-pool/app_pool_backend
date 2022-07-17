package app.pool.project.comment.dto;

import lombok.Data;

@Data
public class CommentCreate {
    private String body;

    private Long messageId;
}
