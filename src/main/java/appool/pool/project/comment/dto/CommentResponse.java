package appool.pool.project.comment.dto;

import appool.pool.project.comment.Comment;
import appool.pool.project.user.PoolUser;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentResponse {

    private Long id;
    private String body;
    private PoolUser writer;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.writer = comment.getWriter();
    }

}
