package app.pool.project.message.repository;

import app.pool.project.message.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {

}
