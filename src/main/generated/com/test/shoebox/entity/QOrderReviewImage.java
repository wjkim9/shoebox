package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderReviewImage is a Querydsl query type for OrderReviewImage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderReviewImage extends EntityPathBase<OrderReviewImage> {

    private static final long serialVersionUID = 251644291L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderReviewImage orderReviewImage = new QOrderReviewImage("orderReviewImage");

    public final StringPath fileName = createString("fileName");

    public final QOrderReview orderReview;

    public final NumberPath<Long> orderReviewImageId = createNumber("orderReviewImageId", Long.class);

    public QOrderReviewImage(String variable) {
        this(OrderReviewImage.class, forVariable(variable), INITS);
    }

    public QOrderReviewImage(Path<? extends OrderReviewImage> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderReviewImage(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderReviewImage(PathMetadata metadata, PathInits inits) {
        this(OrderReviewImage.class, metadata, inits);
    }

    public QOrderReviewImage(Class<? extends OrderReviewImage> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderReview = inits.isInitialized("orderReview") ? new QOrderReview(forProperty("orderReview"), inits.get("orderReview")) : null;
    }

}

