package com.example.evento.listener;

import com.example.evento.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class AuditLogListener {

    private static final Logger logger = LoggerFactory.getLogger(AuditLogListener.class);

    @Async
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        logger.info("------------------------------------------");
        logger.info("REGISTRO DE AUDITORÍA");
        logger.info("Evento: Creación de Pedido");
        logger.info("ID de Pedido: " + event.getOrder().getId());
        logger.info("Email del Cliente: " + event.getOrder().getEmail());
        logger.info("Fecha del Pedido: " + event.getOrder().getOrderDate().format(formatter));
        logger.info("Productos: " + event.getOrder().getProducts());
        logger.info("------------------------------------------");
    }
}