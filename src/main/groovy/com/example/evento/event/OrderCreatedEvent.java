package com.example.evento.event;

import com.example.evento.model.Order;
import org.springframework.context.ApplicationEvent;

public class OrderCreatedEvent extends ApplicationEvent {
    private final Order order;

    public OrderCreatedEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}