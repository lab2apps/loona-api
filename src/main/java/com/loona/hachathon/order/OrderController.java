package com.loona.hachathon.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api", produces = "application/json")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order")
    public void createOrder(@RequestBody OrderRequestDto orderDto) {

    }

    @GetMapping("/order")
    public List<Order> getMyOrders() {
        return orderService.getMyOrders();
    }
}
