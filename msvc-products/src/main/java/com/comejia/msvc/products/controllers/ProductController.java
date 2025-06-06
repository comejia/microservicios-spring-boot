package com.comejia.msvc.products.controllers;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.comejia.msvc.commons.entities.Product;
// import com.comejia.msvc.products.entities.Product;
import com.comejia.msvc.products.services.ProductService;


@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final Logger logger = LoggerFactory.getLogger(ProductController.class);
    final private ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Product>> list() {
        this.logger.info("Llamada a metodo controller ProductController::list()");
        return ResponseEntity.ok(this.service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> details(@PathVariable Long id) throws InterruptedException {
        this.logger.info("Llamada a metodo controller ProductController::details()");
        if (id.equals(10L)) {
            //throw new IllegalArgumentException("Invalid product ID");
        }

        if (id.equals(7L)) {
            //TimeUnit.SECONDS.sleep(3L);
        }

        Optional<Product> productOptional = this.service.findById(id);

        if (productOptional.isPresent()) {
            return ResponseEntity.ok(productOptional.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        this.logger.info("ProductController - Creando producto: {}", product);
        Product newProduct = this.service.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        this.logger.info("ProductController - Actualizando producto: {}", product);
        Optional<Product> productOptional = this.service.findById(id);

        if (productOptional.isPresent()) {
            Product productDb = productOptional.get();
            productDb.setName(product.getName());
            productDb.setPrice(product.getPrice());
            productDb.setCreateAt(product.getCreateAt());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.service.save(productDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        this.logger.info("ProductController - Eliminando producto con id: {}", id);
        Optional<Product> productOptional = this.service.findById(id);

        if (productOptional.isPresent()) {
            this.service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
