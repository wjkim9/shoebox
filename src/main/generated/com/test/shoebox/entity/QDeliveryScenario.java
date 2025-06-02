package com.test.shoebox.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryScenario is a Querydsl query type for DeliveryScenario
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryScenario extends EntityPathBase<DeliveryScenario> {

    private static final long serialVersionUID = 1929366642L;

    public static final QDeliveryScenario deliveryScenario = new QDeliveryScenario("deliveryScenario");

    public final NumberPath<Long> deliveryScenarioId = createNumber("deliveryScenarioId", Long.class);

    public final StringPath location = createString("location");

    public final StringPath status = createString("status");

    public final StringPath step = createString("step");

    public final NumberPath<Integer> waitTime = createNumber("waitTime", Integer.class);

    public QDeliveryScenario(String variable) {
        super(DeliveryScenario.class, forVariable(variable));
    }

    public QDeliveryScenario(Path<? extends DeliveryScenario> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryScenario(PathMetadata metadata) {
        super(DeliveryScenario.class, metadata);
    }

}

