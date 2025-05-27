package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductPostImage is a Querydsl query type for ProductPostImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductPostImage extends EntityPathBase<ProductPostImage> {

    private static final long serialVersionUID = 1304428858L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductPostImage productPostImage = new QProductPostImage("productPostImage");

    public final StringPath fileName = createString("fileName");

    public final QProductPost productPost;

    public final NumberPath<Long> productPostImageId = createNumber("productPostImageId", Long.class);

    public QProductPostImage(String variable) {
        this(ProductPostImage.class, forVariable(variable), INITS);
    }

    public QProductPostImage(Path<? extends ProductPostImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductPostImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductPostImage(PathMetadata metadata, PathInits inits) {
        this(ProductPostImage.class, metadata, inits);
    }

    public QProductPostImage(Class<? extends ProductPostImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productPost = inits.isInitialized("productPost") ? new QProductPost(forProperty("productPost"), inits.get("productPost")) : null;
    }

}

