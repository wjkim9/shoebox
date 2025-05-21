package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductGroup is a Querydsl query type for ProductGroup
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductGroup extends EntityPathBase<ProductGroup> {

    private static final long serialVersionUID = 909417118L;

    public static final QProductGroup productGroup = new QProductGroup("productGroup");

    public final NumberPath<Long> productGroupId = createNumber("productGroupId", Long.class);

    public final StringPath productGroupName = createString("productGroupName");

    public QProductGroup(String variable) {
        super(ProductGroup.class, forVariable(variable));
    }

    public QProductGroup(Path<? extends ProductGroup> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductGroup(PathMetadata metadata) {
        super(ProductGroup.class, metadata);
    }

}

