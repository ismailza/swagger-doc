package me.ismailzahir.swaggerdoc.service;

import lombok.extern.slf4j.Slf4j;
import me.ismailzahir.swaggerdoc.dto.ProductDTO;
import me.ismailzahir.swaggerdoc.model.Product;
import me.ismailzahir.swaggerdoc.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getProducts_returnsPaginatedList() {
        // Arrange
        Pageable pageable = PageRequest.of(0, 10);
        Product product = createTestProduct(1L);
        Page<Product> productPage = new PageImpl<>(Collections.singletonList(product), pageable, 1);

        // Ensure the mock repository returns a non-null value
        when(productRepository.findAll(pageable)).thenReturn(productPage);

        // Act
        Page<ProductDTO> result = productService.getProducts(pageable);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test Product", result.getContent().getFirst().getName());
    }

    @Test
    void saveProduct_savesAndReturnsProduct() {
        // Arrange
        ProductDTO productDTO = createTestProductDTO(1L, "Test Product", "Test Description", 100.0, 10);
        Product product = createTestProduct(1L);

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        ProductDTO result = productService.saveProduct(productDTO);

        // Assert
        assertEquals("Test Product", result.getName());
    }

    @Test
    void updateProduct_updatesAndReturnsProduct() {
        // Arrange
        Long id = 1L;
        ProductDTO productDTO = createTestProductDTO(null, "Updated Product", "Updated Description", 200.0, 20);
        Product existingProduct = createTestProduct(id);

        when(productRepository.findById(id)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        ProductDTO result = productService.updateProduct(id, productDTO);

        // Assert
        assertEquals("Updated Product", result.getName());
    }

    @Test
    void updateProduct_throwsExceptionWhenProductNotFound() {
        // Arrange
        Long id = 1L;
        ProductDTO productDTO = createTestProductDTO(null, "Updated Product", "Updated Description", 200.0, 20);

        when(productRepository.findById(id)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> productService.updateProduct(id, productDTO));
    }

    @Test
    void deleteProduct_deletesProduct() {
        // Arrange
        Long id = 1L;

        doNothing().when(productRepository).deleteById(id);

        // Act
        productService.deleteProduct(id);

        // Assert
        verify(productRepository, times(1)).deleteById(id);
    }

    /**
     * Helper method to create a Product instance for testing
     *
     * @return the created Product instance
     */
    private Product createTestProduct(Long id) {
        return Product.builder()
                .id(id)
                .name("Test Product")
                .description("Test Description")
                .price(100.0)
                .quantity(10)
                .build();
    }

    /**
     * Helper method to create a ProductDTO instance for testing
     * @param id the ID of the product
     * @param name the name of the product
     * @param description the description of the product
     * @param price the price of the product
     * @param quantity the quantity of the product
     * @return the created ProductDTO instance
     */
    private ProductDTO createTestProductDTO(Long id, String name, String description, double price, int quantity) {
        return ProductDTO.builder()
                .id(id)
                .name(name)
                .description(description)
                .price(price)
                .quantity(quantity)
                .build();
    }
}
