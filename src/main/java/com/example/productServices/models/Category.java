package com.example.productServices.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Category extends BaseModal {
    @Column(unique = true,nullable = false)
    @NotNull(message = "Name is a mandatory field")
    @Size(min = 3,max = 10,message = "Name of the category should be between 3 to 10")
    private String name;

    @Builder
    public Category(Long id,String name){
        super(id);
        this.name=name;
    }
}
