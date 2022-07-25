package appool.pool.project.message.repository;

import appool.pool.project.message.Message;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

    @EntityGraph(attributePaths = {"writer"})
    Optional<Message> findWithWriterById(Long id);

}
