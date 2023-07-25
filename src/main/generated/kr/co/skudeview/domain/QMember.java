package kr.co.skudeview.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 199618526L;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final DatePath<java.time.LocalDate> birthDate = createDate("birthDate", java.time.LocalDate.class);

    public final ListPath<Company, QCompany> companies = this.<Company, QCompany>createList("companies", Company.class, QCompany.class, PathInits.DIRECT2);

    //inherited
    public final BooleanPath deleteAt = _super.deleteAt;

    public final StringPath email = createString("email");

    public final EnumPath<kr.co.skudeview.domain.enums.Gender> gender = createEnum("gender", kr.co.skudeview.domain.enums.Gender.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath major = createString("major");

    public final ListPath<MemberSkill, QMemberSkill> memberSkills = this.<MemberSkill, QMemberSkill>createList("memberSkills", MemberSkill.class, QMemberSkill.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modDate = _super.modDate;

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> regDate = _super.regDate;

    public final EnumPath<kr.co.skudeview.domain.enums.Role> role = createEnum("role", kr.co.skudeview.domain.enums.Role.class);

    public final StringPath telephone = createString("telephone");

    public final StringPath univName = createString("univName");

    public QMember(String variable) {
        super(Member.class, forVariable(variable));
    }

    public QMember(Path<? extends Member> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMember(PathMetadata metadata) {
        super(Member.class, metadata);
    }

}

