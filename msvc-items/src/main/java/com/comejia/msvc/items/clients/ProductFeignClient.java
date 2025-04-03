package com.comejia.msvc.items.clients;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.comejia.msvc.items.models.Product;

@FeignClient(name = "msvc-products")
public interface ProductFeignClient {

    @GetMapping("/api/v1/products")
    List<Product> findAll();

    @GetMapping("/api/v1/products/{id}")
    Product details(@PathVariable Long id);
}
