package com.comejia.msvc.items.services;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.comejia.msvc.items.clients.ProductFeignClient;
import com.comejia.msvc.items.models.Item;
import com.comejia.msvc.items.models.Product;

import feign.FeignException;

//@Primary
@Service
public class ItemServiceFeign implements ItemService {

    @Autowired
    private ProductFeignClient client;

    @Override
    public List<Item> findAll() {
        return client.findAll().stream()
            .map(product -> new Item(product, new Random().nextInt(10) + 1))
            .toList();
    }

    @Override
    public Optional<Item> findById(Long id) {
        try {
            Product product = client.details(id);
            return Optional.of(new Item(product, new Random().nextInt(10) + 1));
        } catch (FeignException e) {
            return Optional.empty();
        }        
    }

}
