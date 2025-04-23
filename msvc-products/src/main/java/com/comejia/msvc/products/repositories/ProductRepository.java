package com.comejia.msvc.products.repositories;

import org.springframework.data.repository.CrudRepository;

import com.comejia.msvc.commons.entities.Product;
// import com.comejia.msvc.products.entities.Product;

public interface ProductRepository extends CrudRepository<Product, Long> {

}
