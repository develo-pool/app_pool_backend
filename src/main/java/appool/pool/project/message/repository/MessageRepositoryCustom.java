package appool.pool.project.message.repository;

import appool.pool.project.message.Message;
import appool.pool.project.message.dto.MessageSearch;

import java.util.List;

public interface MessageRepositoryCustom {

    List<Message> getList(MessageSearch messageSearch);
}
