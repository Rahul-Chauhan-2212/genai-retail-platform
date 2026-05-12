package com.genai.retail.product.repository;

import com.genai.retail.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

  Page<Product> findByCategory(String category, Pageable pageable);

  Page<Product> findByBrand(String brand, Pageable pageable);

  Page<Product> findByNameContainingIgnoreCase(
      String keyword,
      Pageable pageable
  );
}