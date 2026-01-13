package com.example.spring_boot_testing;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Item {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Size(min = 2, max = 69, message = "this field size shall be between 2")
    private String name;

    @Positive(message = "this field can not be nigative")
    @Min(1)
    @Max(69)
    private double price;

    @Email
    @NotNull
    @NotEmpty
    @NotBlank
    private String email = "fuckthisplace@gmail.com";

    public Item(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Item(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    
}
