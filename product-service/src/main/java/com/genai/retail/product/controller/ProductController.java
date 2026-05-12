package com.genai.retail.product.controller;

import com.genai.retail.product.dto.ProductPageResponse;
import com.genai.retail.product.dto.ProductRequest;
import com.genai.retail.product.dto.ProductResponse;
import com.genai.retail.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
@Tag(name = "Product Management API", description = "Endpoints for managing products in the catalog")
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "Create a new product", description = "Adds a new product to the catalog")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Product successfully created",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProductResponse.class)) }),
      @ApiResponse(responseCode = "400", description = "Invalid product details supplied",
          content = @Content)
  })
  @PostMapping
  public ProductResponse create(
      @Parameter(description = "Product details to be saved", required = true)
      @Valid @RequestBody ProductRequest request
  ) {
    return productService.createProduct(request);
  }

  @Operation(summary = "Get all products", description = "Retrieves a paginated list of all products")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
      content = { @Content(mediaType = "application/json",
          schema = @Schema(implementation = ProductPageResponse.class)) })
  @GetMapping
  public ProductPageResponse getAll(
      @Parameter(description = "Page number for pagination (0-based)")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Number of items per page")
      @RequestParam(defaultValue = "10") int size
  ) {
    return productService.getAllProducts(page, size);
  }

  @Operation(summary = "Get a product by ID", description = "Retrieves specific product details using its unique identifier")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successfully retrieved the product",
          content = { @Content(mediaType = "application/json",
              schema = @Schema(implementation = ProductResponse.class)) }),
      @ApiResponse(responseCode = "404", description = "Product not found with the given ID",
          content = @Content)
  })
  @GetMapping("/{id}")
  public ProductResponse getById(
      @Parameter(description = "Unique identifier of the product", required = true)
      @PathVariable Long id
  ) {
    return productService.getProduct(id);
  }

  @Operation(summary = "Get products by category", description = "Retrieves a paginated list of products filtered by category name")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved products",
      content = { @Content(mediaType = "application/json",
          schema = @Schema(implementation = ProductPageResponse.class)) })
  @GetMapping("/category/{category}")
  public ProductPageResponse getByCategory(
      @Parameter(description = "The category name to filter by", required = true)
      @PathVariable String category,
      @Parameter(description = "Page number for pagination (0-based)")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Number of items per page")
      @RequestParam(defaultValue = "10") int size
  ) {
    return productService.getByCategory(category, page, size);
  }


  @Operation(summary = "Search products", description = "Retrieves a paginated list of products matching the provided keyword in their name or description")
  @ApiResponse(responseCode = "200", description = "Successfully retrieved matching products",
      content = { @Content(mediaType = "application/json",
          schema = @Schema(implementation = ProductPageResponse.class)) })
  @GetMapping("/search")
  public ProductPageResponse searchProducts(
      @Parameter(description = "Keyword to search for in product names and descriptions", required = true)
      @RequestParam String keyword,
      @Parameter(description = "Page number for pagination (0-based)")
      @RequestParam(defaultValue = "0") int page,
      @Parameter(description = "Number of items per page")
      @RequestParam(defaultValue = "10") int size
  ) {
    return productService.searchProducts(
        keyword,
        page,
        size
    );
  }
}
