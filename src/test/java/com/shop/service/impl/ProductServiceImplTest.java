package com.shop.service.impl;

import com.shop.entity.Product;
import com.shop.repository.ProductRepository;
import com.shop.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    @Test
    public void addProductToCartShouldReturnProduct() {
        Product product = new Product();
        when(productRepository.save(eq(product))).thenReturn(product);

        Product resultProduct = productService.addProductToCart(product);

        assertEquals(product, resultProduct);
    }

    @Test
    public void updateProductShouldCallSaveMethod() {
        Product product = new Product();
        productService.updateProduct(product);

        verify(productRepository).save(product);
    }

    @Test
    public void getProductByIdShouldReturnOptionalOfProduct() {
        Optional<Product> product = Optional.of(new Product());
        when(productRepository.findById(anyInt())).thenReturn(product);

        Optional<Product> resultProduct = productService.getProductById(1, 1);

        assertEquals(product, resultProduct);
    }

}
