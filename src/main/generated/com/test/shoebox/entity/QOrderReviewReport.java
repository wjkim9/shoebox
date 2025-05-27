package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrderReviewReport is a Querydsl query type for OrderReviewReport
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrderReviewReport extends EntityPathBase<OrderReviewReport> {

    private static final long serialVersionUID = -538232308L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrderReviewReport orderReviewReport = new QOrderReviewReport("orderReviewReport");

    public final QOrderReview orderReview;

    public final NumberPath<Long> orderReviewReportId = createNumber("orderReviewReportId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> registerDate = createDateTime("registerDate", java.time.LocalDateTime.class);

    public final StringPath reportReason = createString("reportReason");

    public QOrderReviewReport(String variable) {
        this(OrderReviewReport.class, forVariable(variable), INITS);
    }

    public QOrderReviewReport(Path<? extends OrderReviewReport> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrderReviewReport(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrderReviewReport(PathMetadata metadata, PathInits inits) {
        this(OrderReviewReport.class, metadata, inits);
    }

    public QOrderReviewReport(Class<? extends OrderReviewReport> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orderReview = inits.isInitialized("orderReview") ? new QOrderReview(forProperty("orderReview"), inits.get("orderReview")) : null;
    }

}

