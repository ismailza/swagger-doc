package me.ismailzahir.swaggerdoc.service;

import lombok.RequiredArgsConstructor;
import me.ismailzahir.swaggerdoc.dto.ProductDTO;
import me.ismailzahir.swaggerdoc.model.Product;
import me.ismailzahir.swaggerdoc.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service class for managing products.
 */
@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    /**
     * Retrieves a paginated list of products.
     *
     * @param pageable the pagination information
     * @return a paginated list of products
     */
    public Page<ProductDTO> getProducts(Pageable pageable) {
        return this.productRepository.findAll(pageable)
                .map(product -> ProductDTO.builder()
                        .id(product.getId())
                        .name(product.getName())
                        .description(product.getDescription())
                        .price(product.getPrice())
                        .quantity(product.getQuantity())
                        .build());
    }

    /**
     * Saves a product to the repository.
     *
     * @param productDTO the product to save
     * @return the saved product
     */
    public ProductDTO saveProduct(ProductDTO productDTO) {
        return this.toDTO(this.productRepository.save(this.toEntity(productDTO)));
    }

    /**
     * Updates an existing product in the repository.
     *
     * @param id the ID of the product to update
     * @param productDTO the product data to update
     * @return the updated product
     * @throws IllegalArgumentException if the product is not found
     */
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setQuantity(productDTO.getQuantity());
        return this.toDTO(this.productRepository.save(product));
    }

    /**
     * Deletes a product from the repository by its ID.
     *
     * @param id the ID of the product to delete
     */
    public void deleteProduct(Long id) {
        this.productRepository.deleteById(id);
    }

    /**
     * Converts a ProductDTO to a Product entity.
     *
     * @param productDTO the product data transfer object to convert
     * @return the converted Product entity
     */
    public Product toEntity(ProductDTO productDTO) {
        return Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .quantity(productDTO.getQuantity())
                .build();
    }

    /**
     * Converts a Product entity to a ProductDTO.
     *
     * @param product the product entity to convert
     * @return the converted ProductDTO
     */
    public ProductDTO toDTO(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .quantity(product.getQuantity())
                .build();
    }
}
