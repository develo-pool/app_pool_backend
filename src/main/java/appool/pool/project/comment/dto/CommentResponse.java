package appool.pool.project.comment.dto;

import appool.pool.project.comment.Comment;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CommentResponse {

    private final Long id;
    private final String body;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
    }

    @Builder
    public CommentResponse(Long id, String body) {
        this.id = id;
        this.body = body;
    }
}
