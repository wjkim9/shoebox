package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QDeliveryProgress is a Querydsl query type for DeliveryProgress
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryProgress extends EntityPathBase<DeliveryProgress> {

    private static final long serialVersionUID = 1703877391L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QDeliveryProgress deliveryProgress = new QDeliveryProgress("deliveryProgress");

    public final StringPath currentDeliveryStep = createString("currentDeliveryStep");

    public final DateTimePath<java.time.LocalDateTime> currentStepDatetime = createDateTime("currentStepDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Long> deliveryProgressId = createNumber("deliveryProgressId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> nextStepDatetime = createDateTime("nextStepDatetime", java.time.LocalDateTime.class);

    public final QOrders orders;

    public QDeliveryProgress(String variable) {
        this(DeliveryProgress.class, forVariable(variable), INITS);
    }

    public QDeliveryProgress(Path<? extends DeliveryProgress> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QDeliveryProgress(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QDeliveryProgress(PathMetadata metadata, PathInits inits) {
        this(DeliveryProgress.class, metadata, inits);
    }

    public QDeliveryProgress(Class<? extends DeliveryProgress> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
    }

}

