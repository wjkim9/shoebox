package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrders is a Querydsl query type for Orders
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrders extends EntityPathBase<Orders> {

    private static final long serialVersionUID = 1215330611L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrders orders = new QOrders("orders");

    public final StringPath destinationDetailAddress = createString("destinationDetailAddress");

    public final StringPath destinationJibunAddress = createString("destinationJibunAddress");

    public final StringPath destinationReference = createString("destinationReference");

    public final StringPath destinationRoadAddress = createString("destinationRoadAddress");

    public final NumberPath<Integer> destinationZipCode = createNumber("destinationZipCode", Integer.class);

    public final QIssuedCoupon issuedCoupon;

    public final QMembers members;

    public final DateTimePath<java.time.LocalDateTime> ordersDate = createDateTime("ordersDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> ordersId = createNumber("ordersId", Long.class);

    public final NumberPath<Integer> ordersStatus = createNumber("ordersStatus", Integer.class);

    public final NumberPath<Integer> paymentAmount = createNumber("paymentAmount", Integer.class);

    public final StringPath paymentInfo = createString("paymentInfo");

    public final StringPath paymentMethod = createString("paymentMethod");

    public final NumberPath<Integer> paymentPoint = createNumber("paymentPoint", Integer.class);

    public final StringPath receiverContact = createString("receiverContact");

    public final StringPath receiverEmail = createString("receiverEmail");

    public final StringPath receiverName = createString("receiverName");

    public final NumberPath<Integer> shippingFee = createNumber("shippingFee", Integer.class);

    public QOrders(String variable) {
        this(Orders.class, forVariable(variable), INITS);
    }

    public QOrders(Path<? extends Orders> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrders(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrders(PathMetadata metadata, PathInits inits) {
        this(Orders.class, metadata, inits);
    }

    public QOrders(Class<? extends Orders> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.issuedCoupon = inits.isInitialized("issuedCoupon") ? new QIssuedCoupon(forProperty("issuedCoupon"), inits.get("issuedCoupon")) : null;
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
    }

}

