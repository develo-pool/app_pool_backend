package appool.pool.project.comment.repository;

import appool.pool.project.comment.Comment;
import appool.pool.project.comment.service.CommentSearch;

import java.util.List;

public interface CommentRepositoryCustom {

    List<Comment> getList(CommentSearch commentSearch);
}
