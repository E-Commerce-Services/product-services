package com.example.productServices.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Product extends BaseModal{
    @Column(nullable = false,unique = true)
    @NotNull(message = "Name is a mandatory field")
    @Size(min=3,max = 200, message = "Name of the product should be between 3 to 200")
    private String name;

    @ManyToOne(optional = false)
    @NotNull(message = "Category is a mandatory field")
    private Category category;

    @Column(nullable = false)
    @NotNull(message = "Price is a mandatory field")
    private Double price;

    @Column(nullable = false)
    @NotNull(message = "Description is a mandatory field")
    @Size(min=50,max = 500,message = "Description of the product should be between 50 to 500")
    private String description;

    @Builder
    public Product(Long id,String name, Category category, Double price, String description) {
        super(id);
        this.name = name;
        this.category = category;
        this.price = price;
        this.description = description;
    }
}
