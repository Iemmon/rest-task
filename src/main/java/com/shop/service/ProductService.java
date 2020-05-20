package com.shop.service;

import com.shop.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    Product addProductToCart(Product product);

    Optional<Product> getProductById(Integer userId, Integer productId);

    void updateProduct(Product product);

    void deleteProduct(Integer userId, Integer productId);

    List<Product> getProductsByUserId(Integer userId);
}
