package appool.pool.project.user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPoolUser is a Querydsl query type for PoolUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPoolUser extends EntityPathBase<PoolUser> {

    private static final long serialVersionUID = -1036781178L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPoolUser poolUser = new QPoolUser("poolUser");

    public final appool.pool.project.domain.QBaseTimeEntity _super = new appool.pool.project.domain.QBaseTimeEntity(this);

    public final StringPath birthday = createString("birthday");

    public final appool.pool.project.brand_user.QBrandUser brandUser;

    public final ListPath<String, StringPath> category = this.<String, StringPath>createList("category", String.class, StringPath.class, PathInits.DIRECT2);

    public final ListPath<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment> commentList = this.<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment>createList("commentList", appool.pool.project.comment.Comment.class, appool.pool.project.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath fcmToken = createString("fcmToken");

    public final StringPath gender = createString("gender");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final ListPath<appool.pool.project.message.Message, appool.pool.project.message.QMessage> messageList = this.<appool.pool.project.message.Message, appool.pool.project.message.QMessage>createList("messageList", appool.pool.project.message.Message.class, appool.pool.project.message.QMessage.class, PathInits.DIRECT2);

    public final StringPath nickName = createString("nickName");

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final BooleanPath privacyAgreement = createBoolean("privacyAgreement");

    public final StringPath refreshToken = createString("refreshToken");

    public final BooleanPath termAgreement = createBoolean("termAgreement");

    public final StringPath username = createString("username");

    public final EnumPath<UserStatus> userStatus = createEnum("userStatus", UserStatus.class);

    public final appool.pool.project.welcome.QWelcomeMessage welcomeMessage;

    public QPoolUser(String variable) {
        this(PoolUser.class, forVariable(variable), INITS);
    }

    public QPoolUser(Path<? extends PoolUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPoolUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPoolUser(PathMetadata metadata, PathInits inits) {
        this(PoolUser.class, metadata, inits);
    }

    public QPoolUser(Class<? extends PoolUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brandUser = inits.isInitialized("brandUser") ? new appool.pool.project.brand_user.QBrandUser(forProperty("brandUser"), inits.get("brandUser")) : null;
        this.welcomeMessage = inits.isInitialized("welcomeMessage") ? new appool.pool.project.welcome.QWelcomeMessage(forProperty("welcomeMessage"), inits.get("welcomeMessage")) : null;
    }

}

