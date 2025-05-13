package com.example.evento.config;

import com.example.evento.model.Product;
import com.example.evento.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Autowired
    public DataInitializer(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) {
        // Inicializar productos solo si no hay ninguno
        if (productRepository.count() == 0) {
            System.out.println("Inicializando productos con stock inicial...");

            Product laptop = new Product(null, "Laptop", 10);
            Product mouse = new Product(null, "Mouse", 20);
            Product teclado = new Product(null, "Teclado", 15);

            productRepository.saveAll(Arrays.asList(laptop, mouse, teclado));

            System.out.println("INVENTARIO INICIAL:");
            for (Product product : productRepository.findAll()) {
                System.out.println(product.getName() + ": " + product.getStock() + " unidades");
            }
        }
    }
}