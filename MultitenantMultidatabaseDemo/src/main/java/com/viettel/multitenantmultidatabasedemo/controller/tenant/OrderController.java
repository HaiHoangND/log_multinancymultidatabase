package com.viettel.multitenantmultidatabasedemo.controller.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.OrderInformation;
import com.viettel.multitenantmultidatabasedemo.service.tenant.IOrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1")
public class OrderController {
    private final IOrderService orderService;

    public OrderController(IOrderService orderService) {
        this.orderService = orderService;
    }


    @PostMapping("/addOrder")
    public ResponseEntity<String> save(@RequestBody OrderInformation orderInformation) {
        orderService.saveOrder(orderInformation);
        return ResponseEntity.ok("Thêm thông tin đơn hàng thành công");
    }

    @GetMapping("/orders")
    public List<OrderInformation> getAll() {
        return orderService.getAllOrders();
    }
}
