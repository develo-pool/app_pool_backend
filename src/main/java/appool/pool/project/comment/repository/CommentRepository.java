package appool.pool.project.comment.repository;

import appool.pool.project.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryCustom{

    Comment findCommentByMessageIdAndWriterId(long message_id, long writer_id);

}
