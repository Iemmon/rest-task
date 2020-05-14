package com.shop.controller;

import com.shop.entity.Product;
import com.shop.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/productcart")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<Product> addProduct(@RequestBody Product product) {
        Product resultProduct = productService.addProductToCart(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(resultProduct);
    }

    @GetMapping("/getProduct")
    public ResponseEntity<Product> getProduct(Integer id) {
        Optional<Product> resultProduct = productService.getProductById(id);
        return resultProduct.map(product
                -> ResponseEntity.status(HttpStatus.OK).body(product)).orElseGet(()
                -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(Integer id) {
        if (!productService.getProductById(id).isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody Product product) {
        Integer id = product.getId();
        HttpStatus status = HttpStatus.CREATED;
        if (id != null && productService.getProductById(id).isPresent()) {
            status = HttpStatus.OK;
        }
        productService.updateProduct(product);
        return ResponseEntity.status(status).build();
    }

}
