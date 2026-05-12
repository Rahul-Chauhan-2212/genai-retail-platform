package com.genai.retail.product.mapper;

import com.genai.retail.product.dto.ProductRequest;
import com.genai.retail.product.dto.ProductResponse;
import com.genai.retail.product.entity.Product;

import java.time.LocalDateTime;

public class ProductMapper {

  public static Product toEntity(ProductRequest request) {
    return Product.builder()
        .name(request.getName())
        .category(request.getCategory())
        .description(request.getDescription())
        .brand(request.getBrand())
        .price(request.getPrice())
        .imageUrl(request.getImageUrl())
        .targetAudience(request.getTargetAudience())
        .season(request.getSeason())
        .active(true)
        .createdAt(LocalDateTime.now())
        .updatedAt(LocalDateTime.now())
        .build();
  }

  public static ProductResponse toResponse(Product product) {
    return ProductResponse.builder()
        .id(product.getId())
        .name(product.getName())
        .category(product.getCategory())
        .description(product.getDescription())
        .brand(product.getBrand())
        .price(product.getPrice())
        .imageUrl(product.getImageUrl())
        .targetAudience(product.getTargetAudience())
        .season(product.getSeason())
        .build();
  }
}