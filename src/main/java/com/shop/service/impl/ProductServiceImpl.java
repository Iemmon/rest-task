package com.shop.service.impl;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product addProductToCart(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Optional<Product> getProductById(Integer userId, Integer productId) {
        return productRepository.findProductByUserId(userId, productId);
    }

    @Override
    public void updateProduct(Product product) {
        productRepository.save(product);
    }

    @Override
    public void deleteProduct(Integer userId, Integer productId) {
        productRepository.deleteProductByUserId(userId, productId);
    }

    @Override
    public List<Product> getProductsByUserId(Integer userId){
        return productRepository.findAllProductsByUserId(userId);
    }


}
