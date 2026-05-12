package com.genai.retail.product.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ProductPageResponse {

    private List<ProductResponse> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    private boolean last;
}