package com.cafe.system;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class InventoryBatch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String barcode;

    @ManyToOne
    private Ingredient ingredient;

    private Double quantity;

    private LocalDate expirationDate;

    private boolean deleted = false;

    public InventoryBatch() {}

    public InventoryBatch(
            String barcode,
            Ingredient ingredient,
            Double quantity,
            LocalDate expirationDate
    ) {
        this.barcode = barcode;
        this.ingredient = ingredient;
        this.quantity = quantity;
        this.expirationDate = expirationDate;
    }

    public Long getId() {
        return id;
    }

    public String getBarcode() {
        return barcode;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public boolean isDeleted() {
        return deleted;
    }
}