package me.ismailzahir.swaggerdoc.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * A generic class representing a paginated response containing a list of items and pagination details.
 *
 * @param <T> the type of items in the paginated response
 */
@Data
@AllArgsConstructor
public class PaginatedResponse<T> {
    /**
     * The list of items in the paginated response.
     */
    private List<T> content;

    /**
     * The total number of pages available.
     */
    private int totalPages;

    /**
     * The total number of elements available.
     */
    private long totalElements;

    /**
     * The current page number.
     */
    private int number;

    /**
     * The size of the page (number of items per page).
     */
    private int size;

    /**
     * Constructs a PaginatedResponse from a Page object.
     *
     * @param page the Page object containing pagination information
     */
    public PaginatedResponse(Page<T> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.number = page.getNumber();
        this.size = page.getSize();
    }
}
