package com.cafe.system;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InventoryBatchRepository
        extends JpaRepository<InventoryBatch, Long> {

    List<InventoryBatch>
    findByIngredientIdOrderByExpirationDateAsc(Long ingredientId);
}