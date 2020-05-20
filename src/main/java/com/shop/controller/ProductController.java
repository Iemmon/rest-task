package com.shop.controller;

import com.shop.entity.Product;
import com.shop.entity.User;
import com.shop.exceptionhandler.ResourceNotFoundException;
import com.shop.service.ProductService;
import com.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/users/{userId}/products")
public class ProductController {

    private final ProductService productService;
    private final UserService userService;

    @PostMapping
    public ResponseEntity<Product> addProduct(@RequestBody Product product, @PathVariable Integer userId) {
        User resultUser = userService.getUserById(userId)
                .orElseThrow(ResourceNotFoundException::new);
        product.setCustomer(resultUser);
        Product resultProduct = productService.addProductToCart(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultProduct);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer userId, @PathVariable Integer id) {
        Product resultProduct = productService.getProductById(userId, id)
                .orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK).body(resultProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProductsByUserId(@PathVariable Integer userId) {
        userService.getUserById(userId).orElseThrow(ResourceNotFoundException::new);
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProductsByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Integer userId, @PathVariable Integer id) {
        productService.getProductById(userId, id).orElseThrow(ResourceNotFoundException::new);
        productService.deleteProduct(userId, id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Void> updateProduct(@PathVariable Integer userId, @RequestBody Product product) {
        Integer productId = product.getId();
        HttpStatus status = HttpStatus.CREATED;
        if (productId != null && productService.getProductById(userId, productId).isPresent()) {
            status = HttpStatus.OK;
        }
        productService.updateProduct(product);
        return ResponseEntity.status(status).build();
    }

}
