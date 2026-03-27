package com.ecommerce.controller;

import com.ecommerce.dto.OrderDTO;
import com.ecommerce.service.OrderService;
import com.ecommerce.service.PaymentService;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<OrderDTO> createOrder() {
        return ResponseEntity.ok(orderService.createOrder());
    }

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getUserOrders() {
        return ResponseEntity.ok(orderService.getLoggedInUserOrders());
    }

    @PostMapping("/{orderId}/pay")
    public ResponseEntity<Map<String, String>> payOrder(@PathVariable Long orderId) throws StripeException {
        OrderDTO order = orderService.getLoggedInUserOrders().stream()
                .filter(o -> o.getId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found or unauthorized"));

        PaymentIntent intent = paymentService.createPaymentIntent(order.getTotalAmount());
        
        // In a real app, you'd handle the success/failure from the client side or via webhook.
        // For this demo, we simulate a success and update status.
        orderService.updateOrderStatus(orderId, "PAID");

        Map<String, String> response = new HashMap<>();
        response.put("clientSecret", intent.getClientSecret());
        response.put("status", "Payment Intent Created & Order Marked PAID (Demo)");
        return ResponseEntity.ok(response);
    }
}
