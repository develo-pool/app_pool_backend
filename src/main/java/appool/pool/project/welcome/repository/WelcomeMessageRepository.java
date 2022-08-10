package appool.pool.project.welcome.repository;

import appool.pool.project.message.Message;
import appool.pool.project.welcome.WelcomeMessage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Long> {
    @EntityGraph(attributePaths = {"writer"})
    Optional<WelcomeMessage> findWithWriterById(Long id);
}
