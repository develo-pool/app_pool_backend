package app.pool.project.repository;

import app.pool.project.entity.Message;
import app.pool.project.request.MessageSearch;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getList(MessageSearch messageSearch);
}
