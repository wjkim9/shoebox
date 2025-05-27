package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QEventPost is a Querydsl query type for EventPost
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEventPost extends EntityPathBase<EventPost> {

    private static final long serialVersionUID = -238010036L;

    public static final QEventPost eventPost = new QEventPost("eventPost");

    public final StringPath content = createString("content");

    public final NumberPath<Long> eventPostId = createNumber("eventPostId", Long.class);

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> writeDate = createDateTime("writeDate", java.time.LocalDateTime.class);

    public QEventPost(String variable) {
        super(EventPost.class, forVariable(variable));
    }

    public QEventPost(Path<? extends EventPost> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventPost(PathMetadata metadata) {
        super(EventPost.class, metadata);
    }

}

