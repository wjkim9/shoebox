package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QMainBanner is a Querydsl query type for MainBanner
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMainBanner extends EntityPathBase<MainBanner> {

    private static final long serialVersionUID = 201834899L;

    public static final QMainBanner mainBanner = new QMainBanner("mainBanner");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> mainBannerId = createNumber("mainBannerId", Long.class);

    public final NumberPath<Integer> sortOrder = createNumber("sortOrder", Integer.class);

    public QMainBanner(String variable) {
        super(MainBanner.class, forVariable(variable));
    }

    public QMainBanner(Path<? extends MainBanner> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMainBanner(PathMetadata metadata) {
        super(MainBanner.class, metadata);
    }

}

