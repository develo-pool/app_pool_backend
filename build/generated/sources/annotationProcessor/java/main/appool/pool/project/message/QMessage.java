package appool.pool.project.message;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMessage is a Querydsl query type for Message
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMessage extends EntityPathBase<Message> {

    private static final long serialVersionUID = 331213468L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMessage message = new QMessage("message");

    public final appool.pool.project.domain.QBaseTimeEntity _super = new appool.pool.project.domain.QBaseTimeEntity(this);

    public final StringPath body = createString("body");

    public final ListPath<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment> commentList = this.<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment>createList("commentList", appool.pool.project.comment.Comment.class, appool.pool.project.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<String, StringPath> filePath = this.<String, StringPath>createList("filePath", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath messageLink = createString("messageLink");

    public final EnumPath<appool.pool.project.comment.CommentStatus> status = createEnum("status", appool.pool.project.comment.CommentStatus.class);

    public final StringPath title = createString("title");

    public final appool.pool.project.user.QPoolUser writer;

    public QMessage(String variable) {
        this(Message.class, forVariable(variable), INITS);
    }

    public QMessage(Path<? extends Message> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMessage(PathMetadata metadata, PathInits inits) {
        this(Message.class, metadata, inits);
    }

    public QMessage(Class<? extends Message> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new appool.pool.project.user.QPoolUser(forProperty("writer"), inits.get("writer")) : null;
    }

}

