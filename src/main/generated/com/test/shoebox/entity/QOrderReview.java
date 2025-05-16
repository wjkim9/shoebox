package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderReview is a Querydsl query type for OrderReview
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderReview extends EntityPathBase<OrderReview> {

    private static final long serialVersionUID = 477160120L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderReview orderReview = new QOrderReview("orderReview");

    public final StringPath content = createString("content");

    public final NumberPath<Integer> isBlind = createNumber("isBlind", Integer.class);

    public final NumberPath<Integer> likeCount = createNumber("likeCount", Integer.class);

    public final NumberPath<Long> orderReviewId = createNumber("orderReviewId", Long.class);

    public final QProductStockOrder productStockOrder;

    public final NumberPath<Double> rating = createNumber("rating", Double.class);

    public final DateTimePath<java.time.LocalDateTime> writeDate = createDateTime("writeDate", java.time.LocalDateTime.class);

    public QOrderReview(String variable) {
        this(OrderReview.class, forVariable(variable), INITS);
    }

    public QOrderReview(Path<? extends OrderReview> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderReview(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderReview(PathMetadata metadata, PathInits inits) {
        this(OrderReview.class, metadata, inits);
    }

    public QOrderReview(Class<? extends OrderReview> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.productStockOrder = inits.isInitialized("productStockOrder") ? new QProductStockOrder(forProperty("productStockOrder"), inits.get("productStockOrder")) : null;
    }

}

