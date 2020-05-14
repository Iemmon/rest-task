package com.shop.service;

import com.shop.entity.Product;

import java.util.Optional;

public interface ProductService {

    Product addProductToCart(Product product);

    Optional<Product> getProductById(Integer id);

    Product updateProduct(Product product);

    void deleteProduct(Integer id);
}
