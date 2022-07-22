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

    public static final QPoolUser poolUser = new QPoolUser("poolUser");

    public final appool.pool.project.domain.QBaseTimeEntity _super = new appool.pool.project.domain.QBaseTimeEntity(this);

    public final StringPath birthday = createString("birthday");

    public final ListPath<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment> commentList = this.<appool.pool.project.comment.Comment, appool.pool.project.comment.QComment>createList("commentList", appool.pool.project.comment.Comment.class, appool.pool.project.comment.QComment.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

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

    public QPoolUser(String variable) {
        super(PoolUser.class, forVariable(variable));
    }

    public QPoolUser(Path<? extends PoolUser> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPoolUser(PathMetadata metadata) {
        super(PoolUser.class, metadata);
    }

}

