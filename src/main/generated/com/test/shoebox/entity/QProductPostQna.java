package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPostQna is a Querydsl query type for ProductPostQna
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPostQna extends EntityPathBase<ProductPostQna> {

    private static final long serialVersionUID = 1386838403L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPostQna productPostQna = new QProductPostQna("productPostQna");

    public final StringPath answerContent = createString("answerContent");

    public final DateTimePath<java.time.LocalDateTime> answerDate = createDateTime("answerDate", java.time.LocalDateTime.class);

    public final StringPath answerTitle = createString("answerTitle");

    public final StringPath content = createString("content");

    public final QMembers members;

    public final QProductPost productPost;

    public final NumberPath<Long> productPostQnaId = createNumber("productPostQnaId", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> writeDate = createDateTime("writeDate", java.time.LocalDateTime.class);

    public QProductPostQna(String variable) {
        this(ProductPostQna.class, forVariable(variable), INITS);
    }

    public QProductPostQna(Path<? extends ProductPostQna> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPostQna(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPostQna(PathMetadata metadata, PathInits inits) {
        this(ProductPostQna.class, metadata, inits);
    }

    public QProductPostQna(Class<? extends ProductPostQna> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
        this.productPost = inits.isInitialized("productPost") ? new QProductPost(forProperty("productPost"), inits.get("productPost")) : null;
    }

}

