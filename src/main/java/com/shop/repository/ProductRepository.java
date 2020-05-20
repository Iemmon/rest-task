package com.shop.repository;

import com.shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Query(value = "SELECT p FROM Product p WHERE p.customer.id=?1")
    List<Product> findAllProductsByUserId(Integer id);

    @Query("DELETE FROM Product p WHERE p.customer.id=?1 AND p.id=?2")
    void deleteProductByUserId(Integer userId, Integer productId);

    @Query("SELECT p FROM Product p WHERE p.customer.id=?1 AND p.id=?2")
    Optional<Product> findProductByUserId(Integer userId, Integer productId);
}
