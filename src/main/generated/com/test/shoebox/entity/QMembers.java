package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMembers is a Querydsl query type for Members
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMembers extends EntityPathBase<Members> {

    private static final long serialVersionUID = 1176534155L;

    public static final QMembers members = new QMembers("members");

    public final StringPath accountName = createString("accountName");

    public final StringPath contact = createString("contact");

    public final StringPath email = createString("email");

    public final NumberPath<Double> footSize = createNumber("footSize", Double.class);

    public final NumberPath<Integer> isDeleted = createNumber("isDeleted", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> joinDatetime = createDateTime("joinDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> membersId = createNumber("membersId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> modifiedDatetime = createDateTime("modifiedDatetime", java.time.LocalDateTime.class);

    public final StringPath name = createString("name");

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final NumberPath<Integer> point = createNumber("point", Integer.class);

    public QMembers(String variable) {
        super(Members.class, forVariable(variable));
    }

    public QMembers(Path<? extends Members> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMembers(PathMetadata metadata) {
        super(Members.class, metadata);
    }

}

