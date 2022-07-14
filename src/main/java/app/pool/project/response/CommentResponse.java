package app.pool.project.response;

import app.pool.project.domain.Comment;
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
