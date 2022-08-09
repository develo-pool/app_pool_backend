package appool.pool.project.message.repository;

import appool.pool.project.message.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Message> findWithWriterById(Long id);

    @Query(value = "SELECT * FROM message WHERE writer_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId) ORDER BY message_id DESC", nativeQuery = true)
    List<Message> mainFeed(long sessionId, Pageable pageable);

    @Query(value = "SELECT * FROM message WHERE writer_id IN (SELECT to_user_id FROM follow WHERE from_user_id = :sessionId) AND message_id < :id ORDER BY message_id DESC", nativeQuery = true)
    List<Message> mainFeedLess(long sessionId, Long id, Pageable pageable);
}
