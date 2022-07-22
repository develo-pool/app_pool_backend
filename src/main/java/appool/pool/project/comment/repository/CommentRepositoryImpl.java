package appool.pool.project.comment.repository;

import appool.pool.project.comment.Comment;
import appool.pool.project.comment.QComment;
import appool.pool.project.comment.service.CommentSearch;
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
