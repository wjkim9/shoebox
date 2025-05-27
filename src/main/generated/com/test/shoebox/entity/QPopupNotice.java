package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QPopupNotice is a Querydsl query type for PopupNotice
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QPopupNotice extends EntityPathBase<PopupNotice> {

    private static final long serialVersionUID = -710054634L;

    public static final QPopupNotice popupNotice = new QPopupNotice("popupNotice");

    public final StringPath fileName = createString("fileName");

    public final NumberPath<Long> popupNoticeId = createNumber("popupNoticeId", Long.class);

    public QPopupNotice(String variable) {
        super(PopupNotice.class, forVariable(variable));
    }

    public QPopupNotice(Path<? extends PopupNotice> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPopupNotice(PathMetadata metadata) {
        super(PopupNotice.class, metadata);
    }

}

