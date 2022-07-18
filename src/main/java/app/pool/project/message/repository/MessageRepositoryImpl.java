package app.pool.project.message.repository;

import app.pool.project.message.Message;
import app.pool.project.message.QMessage;
import app.pool.project.message.dto.MessageSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Message> getList(MessageSearch messageSearch) {
        return jpaQueryFactory.selectFrom(QMessage.message)
                .limit(messageSearch.getSize())
                .offset(messageSearch.getOffset())
                .orderBy(QMessage.message.id.desc())
                .fetch();
    }
}