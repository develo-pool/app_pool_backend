package appool.pool.project.comment.dto;


import appool.pool.project.comment.Comment;

public record CommentCreate(String body) {

    public Comment toEntity() {
        return Comment.builder()
                .body(body)
                .build();
    }
}
