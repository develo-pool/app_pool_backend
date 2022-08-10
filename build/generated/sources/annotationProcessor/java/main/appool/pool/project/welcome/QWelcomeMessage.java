package appool.pool.project.welcome;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QWelcomeMessage is a Querydsl query type for WelcomeMessage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QWelcomeMessage extends EntityPathBase<WelcomeMessage> {

    private static final long serialVersionUID = 1128243541L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QWelcomeMessage welcomeMessage = new QWelcomeMessage("welcomeMessage");

    public final appool.pool.project.domain.QBaseTimeEntity _super = new appool.pool.project.domain.QBaseTimeEntity(this);

    public final StringPath body = createString("body");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final ListPath<String, StringPath> filePath = this.<String, StringPath>createList("filePath", String.class, StringPath.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final StringPath messageLink = createString("messageLink");

    public final StringPath title = createString("title");

    public final appool.pool.project.user.QPoolUser writer;

    public QWelcomeMessage(String variable) {
        this(WelcomeMessage.class, forVariable(variable), INITS);
    }

    public QWelcomeMessage(Path<? extends WelcomeMessage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QWelcomeMessage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QWelcomeMessage(PathMetadata metadata, PathInits inits) {
        this(WelcomeMessage.class, metadata, inits);
    }

    public QWelcomeMessage(Class<? extends WelcomeMessage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.writer = inits.isInitialized("writer") ? new appool.pool.project.user.QPoolUser(forProperty("writer"), inits.get("writer")) : null;
    }

}

