package com.genai.retail.product.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductResponse {

  private Long id;

  private String name;

  private String category;

  private String description;

  private String brand;

  private BigDecimal price;

  private String imageUrl;

  private String targetAudience;

  private String season;
}