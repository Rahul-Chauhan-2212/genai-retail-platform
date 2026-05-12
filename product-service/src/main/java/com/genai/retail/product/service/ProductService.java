package com.genai.retail.product.service;

import com.genai.retail.product.dto.ProductPageResponse;
import com.genai.retail.product.dto.ProductRequest;
import com.genai.retail.product.dto.ProductResponse;
import com.genai.retail.product.entity.Product;
import com.genai.retail.product.exception.ProductNotFoundException;
import com.genai.retail.product.mapper.ProductMapper;
import com.genai.retail.product.repository.ProductRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository repository;

  public ProductResponse createProduct(ProductRequest request) {
    log.info("Creating new product with name: {}", request.getName());
    Product product = ProductMapper.toEntity(request);
    Product saved = repository.save(product);
    log.info("Successfully created product with id: {}", saved.getId());
    return ProductMapper.toResponse(saved);
  }

  public ProductPageResponse getAllProducts(Integer page, Integer size) {
    log.info("Fetching all products for page: {}, size: {}", page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = repository.findAll(pageable);
    log.debug("Found {} products out of total {}", products.getNumberOfElements(), products.getTotalElements());
    
    List<ProductResponse> response =
        products.getContent()
            .stream()
            .map(ProductMapper::toResponse)
            .toList();
            
    return ProductPageResponse.builder()
        .content(response)
        .page(products.getNumber())
        .size(products.getSize())
        .totalElements(products.getTotalElements())
        .totalPages(products.getTotalPages())
        .last(products.isLast())
        .build();
  }

  public ProductResponse getProduct(Long id) {
    log.info("Fetching product with id: {}", id);
    Product product = repository.findById(id)
        .orElseThrow(() -> {
          log.warn("Product not found with id: {}", id);
          return new ProductNotFoundException("Product not found with id: " + id);
        });
    log.debug("Successfully retrieved product with id: {}", id);
    return ProductMapper.toResponse(product);
  }

  public ProductPageResponse getByCategory(String category, Integer page, Integer size) {
    log.info("Fetching products for category: {}, page: {}, size: {}", category, page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = repository.findByCategory(category, pageable);
    log.debug("Found {} products for category: {}", products.getNumberOfElements(), category);
    
    List<ProductResponse> response =
        products.getContent()
            .stream()
            .map(ProductMapper::toResponse)
            .toList();
            
    return ProductPageResponse.builder()
        .content(response)
        .page(products.getNumber())
        .size(products.getSize())
        .totalElements(products.getTotalElements())
        .totalPages(products.getTotalPages())
        .last(products.isLast())
        .build();
  }

  public ProductPageResponse searchProducts(String keyword, int page, int size) {
    log.info("Searching products with keyword: '{}', page: {}, size: {}", keyword, page, size);
    Pageable pageable = PageRequest.of(page, size);
    Page<Product> products = repository.findByNameContainingIgnoreCase(keyword, pageable);
    log.debug("Found {} products matching keyword: '{}'", products.getNumberOfElements(), keyword);
    
    List<ProductResponse> response =
        products.getContent()
            .stream()
            .map(ProductMapper::toResponse)
            .toList();

    return ProductPageResponse.builder()
        .content(response)
        .page(products.getNumber())
        .size(products.getSize())
        .totalElements(products.getTotalElements())
        .totalPages(products.getTotalPages())
        .last(products.isLast())
        .build();
  }
}