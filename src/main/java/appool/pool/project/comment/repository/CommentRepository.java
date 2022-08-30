package appool.pool.project.comment.repository;

import appool.pool.project.comment.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>{

    Comment findCommentByMessageIdAndWriterId(long message_id, long writer_id);

    @Query(value = "SELECT * FROM comment WHERE message_id = :messageId ORDER BY comment_id DESC", nativeQuery = true)
    List<Comment> commentList(long messageId, Pageable pageable);

    @Query(value = "SELECT * FROM comment WHERE message_id = :messageId AND comment_id < :id ORDER BY comment_id DESC", nativeQuery = true)
    List<Comment> commentListLess(long messageId, Long id, Pageable pageable);

    @Query(value = "SELECT COUNT(*) FROM comment WHERE message_id = :messageId", nativeQuery = true)
    int commentCount(long messageId);
}
