package com.example.evento.listener;

import com.example.evento.event.OrderCreatedEvent;
import com.example.evento.model.Product;
import com.example.evento.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class InventoryUpdateListener {

    private static final Logger logger = LoggerFactory.getLogger(InventoryUpdateListener.class);

    private final ProductRepository productRepository;

    @Autowired
    public InventoryUpdateListener(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Async
    @EventListener
    @Transactional
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        logger.info("------------------------------------------");
        logger.info("ACTUALIZANDO INVENTARIO");
        logger.info("Pedido #" + event.getOrder().getId());

        for (String productName : event.getOrder().getProducts()) {
            Product product = productRepository.findByName(productName);

            if (product != null && product.getStock() > 0) {
                int stockAnterior = product.getStock();
                int nuevoStock = stockAnterior - 1;
                product.setStock(nuevoStock);
                productRepository.save(product);

                logger.info("Producto: " + product.getName() +
                        " - Stock anterior: " + stockAnterior +
                        " - Nuevo stock: " + nuevoStock +
                        " (" + nuevoStock + "/" + stockAnterior + ")");
            } else if (product != null) {
                logger.warn("No hay stock disponible para: " + product.getName() +
                        " (Stock actual: " + product.getStock() + ")");
            } else {
                logger.warn("Producto no encontrado en inventario: " + productName);
            }
        }

        logger.info("------------------------------------------");
        logger.info("INVENTARIO ACTUALIZADO - ESTADO ACTUAL:");
        for (Product product : productRepository.findAll()) {
            logger.info(product.getName() + ": " + product.getStock() + " unidades");
        }
        logger.info("------------------------------------------");
    }
}