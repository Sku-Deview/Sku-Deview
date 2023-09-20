package kr.co.skudeview.domain.post.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPostFile is a Querydsl query type for PostFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPostFile extends EntityPathBase<PostFile> {

    private static final long serialVersionUID = -1670990627L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPostFile postFile = new QPostFile("postFile");

    public final kr.co.skudeview.global.common.QBaseEntity _super = new kr.co.skudeview.global.common.QBaseEntity(this);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath originalFileName = createString("originalFileName");

    public final QPost post;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final StringPath storedFileName = createString("storedFileName");

    public QPostFile(String variable) {
        this(PostFile.class, forVariable(variable), INITS);
    }

    public QPostFile(Path<? extends PostFile> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPostFile(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPostFile(PathMetadata metadata, PathInits inits) {
        this(PostFile.class, metadata, inits);
    }

    public QPostFile(Class<? extends PostFile> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.post = inits.isInitialized("post") ? new QPost(forProperty("post"), inits.get("post")) : null;
    }

}

