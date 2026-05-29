package com.cafe.system;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class InventoryService {

    private final IngredientRepository ingredientRepository;
    private final InventoryBatchRepository batchRepository;

    public InventoryService(
            IngredientRepository ingredientRepository,
            InventoryBatchRepository batchRepository
    ) {
        this.ingredientRepository = ingredientRepository;
        this.batchRepository = batchRepository;
    }

    public Ingredient createIngredient(
            String name,
            String unit
    ) {

        return ingredientRepository.save(
                new Ingredient(name, unit)
        );
    }

    public InventoryBatch addBatch(
            Long ingredientId,
            Double quantity,
            LocalDate expirationDate
    ) {

        Ingredient ingredient =
                ingredientRepository.findById(ingredientId)
                        .orElseThrow();

        String barcode =
                "ING-" +
                ingredientId +
                "-" +
                UUID.randomUUID()
                        .toString()
                        .substring(0, 8);

        InventoryBatch batch =
                new InventoryBatch(
                        barcode,
                        ingredient,
                        quantity,
                        expirationDate
                );

        return batchRepository.save(batch);
    }

    public void consumeIngredient(
            Long ingredientId,
            Double amount
    ) {

        List<InventoryBatch> batches =
                batchRepository
                        .findByIngredientIdOrderByExpirationDateAsc(
                                ingredientId
                        );

        for (InventoryBatch batch : batches) {

            if (amount <= 0)
                break;

            double available =
                    batch.getQuantity();

            if (available >= amount) {

                batch.setQuantity(
                        available - amount
                );

                amount = 0.0;

            } else {

                amount -= available;

                batch.setQuantity(0.0);
            }

            batchRepository.save(batch);
        }

        if (amount > 0) {
            throw new RuntimeException(
                    "Not enough stock"
            );
        }
    }
}