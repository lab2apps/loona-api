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
    public OrderVerifiedDto createOrder(@RequestBody OrderRequestDto orderDto) {
        return orderService.createOrder(orderDto);
    }

    @GetMapping("/order")
    public List<Order> getMyOrders() {
        return orderService.getMyOrders();
    }

    @GetMapping("/order/{id}/submit")
    public void submitOrder(@PathVariable(value = "id") String id) {
        orderService.submitOrder(id);
    }

    @GetMapping("/order/{id}/fail")
    public void failOrder(@PathVariable(value = "id") String id) {
        orderService.failOrder(id);
    }
}
