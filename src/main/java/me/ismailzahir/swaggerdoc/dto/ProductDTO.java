package me.ismailzahir.swaggerdoc.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A data transfer object representing a product.
 * Used for transferring product data between the client and server.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {
    /**
     * The unique identifier of the product.
     */
    private Long id;

    /**
     * The name of the product.
     * Must be between 3 and 30 characters.
     * Cannot be blank.
     */
    @NotBlank(message = "Name is required")
    @Size(min = 3, max = 30)
    private String name;

    /**
     * The description of the product.
     * Must be at least 8 characters.
     * Cannot be blank.
     */
    @NotBlank(message = "Description is required")
    @Size(min = 8)
    private String description;

    /**
     * The price of the product.
     * Must be greater than zero.
     * Cannot be null.
     */
    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private double price;

    /**
     * The quantity of the product.
     * Must be greater than zero.
     * Cannot be null.
     */
    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    private int quantity;
}
