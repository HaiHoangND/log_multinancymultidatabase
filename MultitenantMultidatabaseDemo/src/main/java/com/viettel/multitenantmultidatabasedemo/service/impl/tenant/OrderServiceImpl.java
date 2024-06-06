package com.viettel.multitenantmultidatabasedemo.service.impl.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.OrderInformation;
import com.viettel.multitenantmultidatabasedemo.service.tenant.IOrderService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService {
    private final MongoTemplate mongoTemplate;

    public OrderServiceImpl(@Lazy MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void saveOrder(OrderInformation orderInformation) {
        mongoTemplate.save(orderInformation);
    }

    @Override
    public List<OrderInformation> getAllOrders() {
        return mongoTemplate.findAll(OrderInformation.class);
    }
}
