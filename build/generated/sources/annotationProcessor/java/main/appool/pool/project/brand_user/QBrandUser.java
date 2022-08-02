package appool.pool.project.brand_user;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QBrandUser is a Querydsl query type for BrandUser
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBrandUser extends EntityPathBase<BrandUser> {

    private static final long serialVersionUID = 884152587L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBrandUser brandUser = new QBrandUser("brandUser");

    public final appool.pool.project.domain.QBaseTimeEntity _super = new appool.pool.project.domain.QBaseTimeEntity(this);

    public final BooleanPath brandAgreement = createBoolean("brandAgreement");

    public final ListPath<String, StringPath> brandCategory = this.<String, StringPath>createList("brandCategory", String.class, StringPath.class, PathInits.DIRECT2);

    public final StringPath brandInfo = createString("brandInfo");

    public final StringPath brandProfileImage = createString("brandProfileImage");

    public final StringPath brandUsername = createString("brandUsername");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModifiedDate = _super.lastModifiedDate;

    public final appool.pool.project.user.QPoolUser poolUser;

    public QBrandUser(String variable) {
        this(BrandUser.class, forVariable(variable), INITS);
    }

    public QBrandUser(Path<? extends BrandUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBrandUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBrandUser(PathMetadata metadata, PathInits inits) {
        this(BrandUser.class, metadata, inits);
    }

    public QBrandUser(Class<? extends BrandUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.poolUser = inits.isInitialized("poolUser") ? new appool.pool.project.user.QPoolUser(forProperty("poolUser"), inits.get("poolUser")) : null;
    }

}

