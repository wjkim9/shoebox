package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QPointTransaction is a Querydsl query type for PointTransaction
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPointTransaction extends EntityPathBase<PointTransaction> {

    private static final long serialVersionUID = -300017668L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QPointTransaction pointTransaction = new QPointTransaction("pointTransaction");

    public final QMembers members;

    public final NumberPath<Long> pointTransactionId = createNumber("pointTransactionId", Long.class);

    public final StringPath reason = createString("reason");

    public final DateTimePath<java.time.LocalDateTime> transactionDatetime = createDateTime("transactionDatetime", java.time.LocalDateTime.class);

    public final NumberPath<Integer> transactionPoint = createNumber("transactionPoint", Integer.class);

    public final StringPath transactionType = createString("transactionType");

    public QPointTransaction(String variable) {
        this(PointTransaction.class, forVariable(variable), INITS);
    }

    public QPointTransaction(Path<? extends PointTransaction> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QPointTransaction(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QPointTransaction(PathMetadata metadata, PathInits inits) {
        this(PointTransaction.class, metadata, inits);
    }

    public QPointTransaction(Class<? extends PointTransaction> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.members = inits.isInitialized("members") ? new QMembers(forProperty("members")) : null;
    }

}

