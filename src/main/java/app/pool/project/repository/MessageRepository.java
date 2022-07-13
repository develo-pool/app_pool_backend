package app.pool.project.repository;

import app.pool.project.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
//    Message save(Message message);
//
//    List<Message> findAllByMessageIdx(Integer messageIdx);
}
