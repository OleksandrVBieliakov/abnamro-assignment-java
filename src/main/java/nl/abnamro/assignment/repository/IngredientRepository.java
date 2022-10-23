package nl.abnamro.assignment.repository;

import nl.abnamro.assignment.model.Dish;
import nl.abnamro.assignment.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface IngredientRepository extends JpaRepository<Ingredient, UUID>, QuerydslPredicateExecutor<Dish> {
}
