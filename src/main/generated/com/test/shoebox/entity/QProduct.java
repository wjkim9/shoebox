package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = -81821599L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QProduct product = new QProduct("product");

    public final QBrand brand;

    public final QCategories categories;

    public final NumberPath<Double> discountRate = createNumber("discountRate", Double.class);

    public final QProductGroup productGroup;

    public final NumberPath<Long> productId = createNumber("productId", Long.class);

    public final ListPath<ProductImage, QProductImage> productImages = this.<ProductImage, QProductImage>createList("productImages", ProductImage.class, QProductImage.class, PathInits.DIRECT2);

    public final StringPath productName = createString("productName");

    public final ListPath<ProductPost, QProductPost> productPost = this.<ProductPost, QProductPost>createList("productPost", ProductPost.class, QProductPost.class, PathInits.DIRECT2);

    public final NumberPath<Integer> productPrice = createNumber("productPrice", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> productRegisterDate = createDateTime("productRegisterDate", java.time.LocalDateTime.class);

    public final ListPath<ProductStock, QProductStock> productStock = this.<ProductStock, QProductStock>createList("productStock", ProductStock.class, QProductStock.class, PathInits.DIRECT2);

    public final StringPath targetCustomerType = createString("targetCustomerType");

    public QProduct(String variable) {
        this(Product.class, forVariable(variable), INITS);
    }

    public QProduct(Path<? extends Product> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QProduct(PathMetadata metadata, PathInits inits) {
        this(Product.class, metadata, inits);
    }

    public QProduct(Class<? extends Product> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.categories = inits.isInitialized("categories") ? new QCategories(forProperty("categories")) : null;
        this.productGroup = inits.isInitialized("productGroup") ? new QProductGroup(forProperty("productGroup")) : null;
    }

}

