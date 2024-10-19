package me.ismailzahir.swaggerdoc.repository;

import me.ismailzahir.swaggerdoc.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Product entity
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
