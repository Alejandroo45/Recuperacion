package com.example.evento.service;

import com.example.evento.dto.OrderDTO;
import com.example.evento.event.OrderCreatedEvent;
import com.example.evento.model.Order;
import com.example.evento.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    private final OrderRepository orderRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public OrderService(OrderRepository orderRepository, ApplicationEventPublisher eventPublisher) {
        this.orderRepository = orderRepository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setEmail(orderDTO.getEmail());
        order.setProducts(orderDTO.getProducts());
        order.setOrderDate(LocalDateTime.now());

        Order savedOrder = orderRepository.save(order);
        logger.info("Orden creada con ID: " + savedOrder.getId());

        eventPublisher.publishEvent(new OrderCreatedEvent(this, savedOrder));
        logger.info("Evento OrderCreatedEvent publicado");

        return savedOrder;
    }
}