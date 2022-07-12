package app.pool.project.repository;

import app.pool.project.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long>, MessageRepositoryCustom {
//    Message save(Message message);
//
//    List<Message> findAllByMessageIdx(Integer messageIdx);
}
