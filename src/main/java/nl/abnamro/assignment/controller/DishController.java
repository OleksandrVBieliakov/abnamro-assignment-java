package nl.abnamro.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.dto.*;
import nl.abnamro.assignment.service.DishService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Tag(name = "Dishes",
    description = "API for dishes AKA recipes")
@RestController
@RequestMapping("/dish")
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;

    @Operation(summary = "Create new dish")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    DishDto createDish(@Valid @RequestBody ShortDishDto dish) {
        return dishService.createDish(dish);
    }

    @Operation(summary = "Update the summary part of the dish, ingredients remain")
    @PatchMapping("/{id}")
    DishDto updateDish(@PathVariable UUID id, @Valid @RequestBody ShortDishDto dish) {
        return dishService.updateDish(id, dish);
    }

    @Operation(summary = "Get the dish by ID")
    @GetMapping("/{id}")
    DishDto getDishById(@PathVariable UUID id) {
        return dishService.getDishById(id);
    }

    @Operation(summary = "Get all dishes. The same as getting dishes with empty filer")
    @GetMapping
    Dishes getAllDishes() {
        return dishService.getAllDishes();
    }

    @Operation(summary = "Get dishes by filter")
    @GetMapping("/filter")
    Dishes getDishes(DishFilter filter) {
        return dishService.getDishes(filter);
    }

    @Operation(summary = "Add ingredient to the dish")
    @PostMapping("/{id}/ingredient")
    @ResponseStatus(HttpStatus.CREATED)
    IngredientDto addIngredient(@PathVariable UUID id,  @Valid @RequestBody ShortIngredientDto ingredient) {
        return dishService.addIngredient(id, ingredient);
    }

    @Operation(summary = "Delete ingredient from the dish")
    @DeleteMapping("/{id}/ingredient/{ingredientId}")
    void deleteIngredient(@PathVariable UUID id, @PathVariable UUID ingredientId) {
        dishService.deleteIngredient(id, ingredientId);
    }

    @Operation(summary = "Delete the whole dish")
    @DeleteMapping("/{id}")
    void deleteDish(@PathVariable UUID id) {
        dishService.deleteDish(id);
    }

}
