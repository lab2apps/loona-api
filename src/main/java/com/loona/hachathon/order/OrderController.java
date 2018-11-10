package com.loona.hachathon.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

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
    public Set<LocalDate> getOrdersByRoom(@RequestParam(value = "roomId") String roomId,
                                          @RequestParam(value = "fromDate")
                                          @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
                                          @RequestParam(value = "toDate")
                                              @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        return orderService.getOrdersByRoom(roomId, fromDate, toDate);
    }

//    @GetMapping("/order")
//    public List<Order> getMyOrders() {
//        return orderService.getMyOrders();
//    }

    @GetMapping("/order/{id}/submit")
    public void submitOrder(@PathVariable(value = "id") String id) {
        orderService.submitOrder(id);
    }

    @GetMapping("/order/{id}/fail")
    public void failOrder(@PathVariable(value = "id") String id) {
        orderService.failOrder(id);
    }
}
