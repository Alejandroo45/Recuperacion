package com.example.evento.listener;

import com.example.evento.event.OrderCreatedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(EmailNotificationListener.class);

    @Async
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        logger.info("------------------------------------------");
        logger.info("ENVIANDO CORREO DE CONFIRMACIÓN DE PEDIDO");
        logger.info("Para: " + event.getOrder().getEmail());
        logger.info("Asunto: Confirmación de pedido #" + event.getOrder().getId());
        logger.info("Contenido: Tu pedido ha sido recibido y está siendo procesado.");
        logger.info("Productos: " + event.getOrder().getProducts());
        logger.info("------------------------------------------");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        logger.info("Correo enviado con éxito a " + event.getOrder().getEmail());
    }
}