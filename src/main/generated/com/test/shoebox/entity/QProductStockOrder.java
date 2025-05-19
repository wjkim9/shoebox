package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductStockOrder is a Querydsl query type for ProductStockOrder
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductStockOrder extends EntityPathBase<ProductStockOrder> {

    private static final long serialVersionUID = 1107932441L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductStockOrder productStockOrder = new QProductStockOrder("productStockOrder");

    public final NumberPath<Integer> orderPrice = createNumber("orderPrice", Integer.class);

    public final QOrders orders;

    public final QProductStock productStock;

    public final NumberPath<Long> productStockOrderId = createNumber("productStockOrderId", Long.class);

    public final NumberPath<Integer> quantity = createNumber("quantity", Integer.class);

    public QProductStockOrder(String variable) {
        this(ProductStockOrder.class, forVariable(variable), INITS);
    }

    public QProductStockOrder(Path<? extends ProductStockOrder> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductStockOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductStockOrder(PathMetadata metadata, PathInits inits) {
        this(ProductStockOrder.class, metadata, inits);
    }

    public QProductStockOrder(Class<? extends ProductStockOrder> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.orders = inits.isInitialized("orders") ? new QOrders(forProperty("orders"), inits.get("orders")) : null;
        this.productStock = inits.isInitialized("productStock") ? new QProductStock(forProperty("productStock"), inits.get("productStock")) : null;
    }

}

