package me.ismailzahir.swaggerdoc.model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, updatable = false)
    private Long id;
    private String name;
    @Column(columnDefinition = "text")
    private String description;
    private double price;
    private int quantity;

}
