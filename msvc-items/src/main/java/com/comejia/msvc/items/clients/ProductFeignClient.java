package com.comejia.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.comejia.msvc.commons.entities.Product;
// import com.comejia.msvc.items.models.Product;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping("/api/v1/products")
    List<Product> findAll();

    @GetMapping("/api/v1/products/{id}")
    Product details(@PathVariable Long id);

    @PostMapping("/api/v1/products")
    public Product create(@RequestBody Product product);

    @PutMapping("/api/v1/products/{id}")
    public Product update(@RequestBody Product product, @PathVariable Long id);

    @DeleteMapping("/api/v1/products/{id}")
    public void delete(@PathVariable Long id);
}
