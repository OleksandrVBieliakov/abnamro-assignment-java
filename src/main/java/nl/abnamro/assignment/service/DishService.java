package nl.abnamro.assignment.service;

import nl.abnamro.assignment.dto.*;

import java.util.UUID;

public interface DishService {

    DishDto createDish(ShortDishDto dish);

    @SuppressWarnings("UnusedReturnValue")
    DishDto updateDish(UUID id, ShortDishDto dish);

    DishDto getDishById(UUID id);

    Dishes getAllDishes();

    IngredientDto addIngredient(UUID id, ShortIngredientDto ingredient);

    void deleteIngredient(UUID id, UUID ingredientId);

    void deleteDish(UUID id);

    Dishes getDishes(DishFilter filter);
}
