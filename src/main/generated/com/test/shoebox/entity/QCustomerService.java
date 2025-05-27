package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCustomerService is a Querydsl query type for CustomerService
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCustomerService extends EntityPathBase<CustomerService> {

    private static final long serialVersionUID = -1788147095L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCustomerService customerService = new QCustomerService("customerService");

    public final StringPath answerContent = createString("answerContent");

    public final DateTimePath<java.time.LocalDateTime> answerDate = createDateTime("answerDate", java.time.LocalDateTime.class);

    public final StringPath category = createString("category");

    public final StringPath content = createString("content");

    public final NumberPath<Long> customerServiceId = createNumber("customerServiceId", Long.class);

    public final QMembers members;

    public final QCustomerService parent;

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> writeDate = createDateTime("writeDate", java.time.LocalDateTime.class);

    public QCustomerService(String variable) {
        this(CustomerService.class, forVariable(variable), INITS);
    }

    public QCustomerService(Path<? extends CustomerService> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCustomerService(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCustomerService(PathMetadata metadata, PathInits inits) {
        this(CustomerService.class, metadata, inits);
    }

    public QCustomerService(Class<? extends CustomerService> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
        this.parent = inits.isInitialized("parent") ? new QCustomerService(forProperty("parent"), inits.get("parent")) : null;
    }

}

