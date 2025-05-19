package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QFavoriteBrand is a Querydsl query type for FavoriteBrand
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFavoriteBrand extends EntityPathBase<FavoriteBrand> {

    private static final long serialVersionUID = 820316477L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QFavoriteBrand favoriteBrand = new QFavoriteBrand("favoriteBrand");

    public final QBrand brand;

    public final NumberPath<Long> favoriteBrandId = createNumber("favoriteBrandId", Long.class);

    public final QMembers members;

    public QFavoriteBrand(String variable) {
        this(FavoriteBrand.class, forVariable(variable), INITS);
    }

    public QFavoriteBrand(Path<? extends FavoriteBrand> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QFavoriteBrand(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QFavoriteBrand(PathMetadata metadata, PathInits inits) {
        this(FavoriteBrand.class, metadata, inits);
    }

    public QFavoriteBrand(Class<? extends FavoriteBrand> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.brand = inits.isInitialized("brand") ? new QBrand(forProperty("brand")) : null;
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
    }

}

