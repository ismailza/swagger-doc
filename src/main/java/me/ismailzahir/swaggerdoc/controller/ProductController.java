package me.ismailzahir.swaggerdoc.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.ismailzahir.swaggerdoc.dto.PaginatedResponse;
import me.ismailzahir.swaggerdoc.dto.ProductDTO;
import me.ismailzahir.swaggerdoc.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {
    private final ProductService productService;

    /**
     * Handles HTTP GET requests for retrieving a paginated list of products.
     *
     * @param page the page number to retrieve, defaults to 0 if not specified
     * @param size the number of products per page, defaults to 10 if not specified
     * @return a paginated list of products
     */
    @GetMapping("")
    @Operation(
            summary = "Retrieve paginated list of products",
            description = "Returns a paginated list of products based on the given page number and size."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful retrieval of products"),
            @ApiResponse(responseCode = "400", description = "Invalid page number or size"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<PaginatedResponse<ProductDTO>> index(
            @Parameter(description = "The page number to retrieve", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "The number of products per page", example = "10")
            @RequestParam(defaultValue = "10") int size) {
        log.info("Retrieving products with page number {} and size {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductDTO> productPage = this.productService.getProducts(pageable);
        PaginatedResponse<ProductDTO> response = new PaginatedResponse<>(productPage);
        return ResponseEntity.ok(response);
    }


    /**
     * Handles HTTP POST requests for saving a new product.
     *
     * @param productDTO the product data transfer object to save
     * @return the saved product data transfer object
     */
    @PostMapping("")
    @Operation(
            summary = "Save a new product",
            description = "Saves a new product based on the given product data transfer object."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful saving of product"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> save(
            @Parameter(description = "The product data transfer object to save")
            @Valid @RequestBody ProductDTO productDTO) {
        log.info("Saving product: {}", productDTO);
        ProductDTO savedProduct = this.productService.saveProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
    }

    /**
     * Handles HTTP PUT requests for updating an existing product.
     *
     * @param id         the ID of the product to update
     * @param productDTO the product data transfer object containing updated information
     * @return the updated product data transfer object
     */
    @PutMapping("/{id}/update")
    @Operation(
            summary = "Update an existing product",
            description = "Updates an existing product based on the given product ID and data transfer object."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful update of product"),
            @ApiResponse(responseCode = "400", description = "Invalid product data"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<ProductDTO> update(
            @Parameter(description = "The ID of the product to update") @PathVariable Long id,
            @Parameter(description = "The product data transfer object containing updated information")
            @RequestBody ProductDTO productDTO) {
        log.info("Updating product with id {}: {}", id, productDTO);
        ProductDTO updatedProduct = this.productService.updateProduct(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    /**
     * Handles HTTP DELETE requests for deleting a product by its ID.
     *
     * @param id the ID of the product to delete
     */
    @DeleteMapping("/{id}/delete")
    @Operation(
            summary = "Delete a product by ID",
            description = "Deletes a product based on the given product ID."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful deletion of product"),
            @ApiResponse(responseCode = "404", description = "Product not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Void> delete(@Parameter(description = "The ID of the product to delete") @PathVariable Long id) {
        log.info("Deleting product with id {}", id);
        this.productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}
