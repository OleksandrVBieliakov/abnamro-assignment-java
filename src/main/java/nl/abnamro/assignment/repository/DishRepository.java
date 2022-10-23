package nl.abnamro.assignment.repository;

import nl.abnamro.assignment.model.Dish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;

public interface DishRepository extends JpaRepository<Dish, UUID>, QuerydslPredicateExecutor<Dish> {
}
