package app.pool.project.comment.repository;

import app.pool.project.comment.Comment;
import app.pool.project.comment.service.CommentSearch;
import app.pool.project.domain.QComment;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepositoryCustom{

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Comment> getList(CommentSearch commentSearch) {
        return jpaQueryFactory.selectFrom(QComment.comment)
                .limit(commentSearch.getSize())
                .offset(commentSearch.getOffset())
                .orderBy(QComment.comment.id.desc())
                .fetch();
    }
}
