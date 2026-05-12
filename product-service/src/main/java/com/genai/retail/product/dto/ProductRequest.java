package com.genai.retail.product.dto;

import jakarta.validation.constraints.NotBlank;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class ProductRequest {

  @NotBlank
  private String name;

  private String category;

  private String description;

  private String brand;

  private BigDecimal price;

  private String imageUrl;

  private String targetAudience;

  private String season;
}