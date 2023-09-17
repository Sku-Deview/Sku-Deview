package kr.co.skudeview.domain.member.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMemberLikeReply is a Querydsl query type for MemberLikeReply
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMemberLikeReply extends EntityPathBase<MemberLikeReply> {

    private static final long serialVersionUID = -40370146L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMemberLikeReply memberLikeReply = new QMemberLikeReply("memberLikeReply");

    public final kr.co.skudeview.global.common.QBaseEntity _super = new kr.co.skudeview.global.common.QBaseEntity(this);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QMember member;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final kr.co.skudeview.domain.reply.entity.QReply reply;

    public QMemberLikeReply(String variable) {
        this(MemberLikeReply.class, forVariable(variable), INITS);
    }

    public QMemberLikeReply(Path<? extends MemberLikeReply> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMemberLikeReply(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMemberLikeReply(PathMetadata metadata, PathInits inits) {
        this(MemberLikeReply.class, metadata, inits);
    }

    public QMemberLikeReply(Class<? extends MemberLikeReply> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.member = inits.isInitialized("member") ? new QMember(forProperty("member")) : null;
        this.reply = inits.isInitialized("reply") ? new kr.co.skudeview.domain.reply.entity.QReply(forProperty("reply"), inits.get("reply")) : null;
    }

}

