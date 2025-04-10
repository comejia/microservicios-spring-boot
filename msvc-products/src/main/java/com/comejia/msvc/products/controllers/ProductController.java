package com.comejia.msvc.products.controllers;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comejia.msvc.products.entities.Product;
import com.comejia.msvc.products.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException {
        if (id.equals(10L)) {
            throw new IllegalArgumentException("Invalid product ID");
        }

        if (id.equals(7L)) {
            TimeUnit.SECONDS.sleep(4L);
        }

        Optional<Product> productOptional = this.service.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

}
