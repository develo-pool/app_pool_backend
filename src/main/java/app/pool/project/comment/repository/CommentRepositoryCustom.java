package app.pool.project.comment.repository;

import app.pool.project.comment.Comment;
import app.pool.project.comment.service.CommentSearch;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(CommentSearch commentSearch);
}
