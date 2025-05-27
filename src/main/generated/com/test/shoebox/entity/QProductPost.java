package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPost is a Querydsl query type for ProductPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPost extends EntityPathBase<ProductPost> {

    private static final long serialVersionUID = 1692169377L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPost productPost = new QProductPost("productPost");

    public final StringPath content = createString("content");

    public final DateTimePath<java.time.LocalDateTime> postDate = createDateTime("postDate", java.time.LocalDateTime.class);

    public final QProduct product;

    public final NumberPath<Long> productPostId = createNumber("productPostId", Long.class);

    public final NumberPath<Integer> viewCount = createNumber("viewCount", Integer.class);

    public QProductPost(String variable) {
        this(ProductPost.class, forVariable(variable), INITS);
    }

    public QProductPost(Path<? extends ProductPost> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPost(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPost(PathMetadata metadata, PathInits inits) {
        this(ProductPost.class, metadata, inits);
    }

    public QProductPost(Class<? extends ProductPost> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

