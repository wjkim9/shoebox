package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProductStock is a Querydsl query type for ProductStock
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductStock extends EntityPathBase<ProductStock> {

    private static final long serialVersionUID = 920558389L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProductStock productStock = new QProductStock("productStock");

    public final QProduct product;

    public final NumberPath<Long> productStockId = createNumber("productStockId", Long.class);

    public final ListPath<ProductStockOrder, QProductStockOrder> productStockOrder = this.<ProductStockOrder, QProductStockOrder>createList("productStockOrder", ProductStockOrder.class, QProductStockOrder.class, PathInits.DIRECT2);

    public final StringPath shoeSize = createString("shoeSize");

    public final NumberPath<Integer> stockQuantity = createNumber("stockQuantity", Integer.class);

    public QProductStock(String variable) {
        this(ProductStock.class, forVariable(variable), INITS);
    }

    public QProductStock(Path<? extends ProductStock> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProductStock(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProductStock(PathMetadata metadata, PathInits inits) {
        this(ProductStock.class, metadata, inits);
    }

    public QProductStock(Class<? extends ProductStock> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product"), inits.get("product")) : null;
    }

}

