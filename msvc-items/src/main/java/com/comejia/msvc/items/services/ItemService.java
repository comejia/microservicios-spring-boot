package com.comejia.msvc.items.services;

import java.util.List;
import java.util.Optional;

import com.comejia.msvc.commons.entities.Product;
import com.comejia.msvc.items.models.Item;
// import com.comejia.msvc.items.models.Product;

public interface ItemService {

    List<Item> findAll();

    Optional<Item> findById(Long id);

    Product save(Product product);

    Product update(Product product, Long id);

    void delete(Long id);

}
