package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QVisitorCount is a Querydsl query type for VisitorCount
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QVisitorCount extends EntityPathBase<VisitorCount> {

    private static final long serialVersionUID = -300522225L;

    public static final QVisitorCount visitorCount = new QVisitorCount("visitorCount");

    public final DatePath<java.time.LocalDate> visitDate = createDate("visitDate", java.time.LocalDate.class);

    public QVisitorCount(String variable) {
        super(VisitorCount.class, forVariable(variable));
    }

    public QVisitorCount(Path<? extends VisitorCount> path) {
        super(path.getType(), path.getMetadata());
    }

    public QVisitorCount(PathMetadata metadata) {
        super(VisitorCount.class, metadata);
    }

}

