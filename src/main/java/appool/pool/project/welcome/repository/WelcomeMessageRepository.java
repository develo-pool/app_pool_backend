package appool.pool.project.welcome.repository;

import appool.pool.project.welcome.WelcomeMessage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface WelcomeMessageRepository extends JpaRepository<WelcomeMessage, Long> {
    @Query(value = "SELECT * FROM welcome_message WHERE writer_id = :id", nativeQuery = true)
    Optional<WelcomeMessage> findByWriterId(Long id);
}
