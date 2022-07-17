package app.pool.project.message.repository;

import app.pool.project.message.Message;
import app.pool.project.message.dto.MessageSearch;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getList(MessageSearch messageSearch);
}
