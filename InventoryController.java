package com.cafe.system;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    private final InventoryService service;

    public InventoryController(
            InventoryService service
    ) {
        this.service = service;
    }

    @PostMapping("/ingredient")
    public Ingredient createIngredient(
            @RequestParam String name,
            @RequestParam String unit
    ) {

        return service.createIngredient(
                name,
                unit
        );
    }

    @PostMapping("/batch")
    public InventoryBatch addBatch(
            @RequestParam Long ingredientId,
            @RequestParam Double quantity,
            @RequestParam String expirationDate
    ) {

        return service.addBatch(
                ingredientId,
                quantity,
                LocalDate.parse(expirationDate)
        );
    }

    @PostMapping("/consume")
    public String consume(
            @RequestParam Long ingredientId,
            @RequestParam Double amount
    ) {

        service.consumeIngredient(
                ingredientId,
                amount
        );

        return "Inventory updated";
    }
}