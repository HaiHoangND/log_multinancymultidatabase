package com.viettel.multitenantmultidatabasedemo.service.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.OrderInformation;

import java.util.List;

public interface IOrderService {
    void saveOrder(OrderInformation orderInformation);
    List<OrderInformation> getAllOrders();
}
