package com.comejia.msvc.items.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.comejia.msvc.items.models.Item;
import com.comejia.msvc.items.models.Product;
import com.comejia.msvc.items.services.ItemService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/v1/items")
public class ItemController {

    private final Logger logger = LoggerFactory.getLogger(ItemController.class);
    private final ItemService service;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public ItemController(ItemService service, CircuitBreakerFactory circuitBreakerFactory) {
        this.service = service;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    @GetMapping
    public ResponseEntity<List<Item>> list(
        @RequestHeader(name = "token-request", required = false) String token,
        @RequestParam(name = "name", required = false) String name
    ) {
        System.out.println("Token: " + token);
        System.out.println("Name: " + name);

        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detail(@PathVariable Long id) {
        // Optional<Item> itemOptional = this.service.findById(id);
        Optional<Item> itemOptional = this.circuitBreakerFactory.create("items").run(
            () -> this.service.findById(id), 
            e -> {
                this.logger.error("Error: {}", e.getMessage());
                Product product = new Product();
                product.setId(id);
                product.setName("No existe el producto en el inventario");
                product.setPrice(0.0);
                product.setCreateAt(LocalDate.now());
                return Optional.of(new Item(product, 0));
            }
        );

        if (itemOptional.isPresent()) {
            return ResponseEntity.ok(itemOptional.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Collections.singletonMap("message", "No existe el producto en el inventario"));
    }
}
