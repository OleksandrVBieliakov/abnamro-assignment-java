package nl.abnamro.assignment.repository;

import nl.abnamro.assignment.model.Dish;
import nl.abnamro.assignment.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID>, QuerydslPredicateExecutor<Dish> {
    Optional<Product> findFirstByNameIgnoreCase(String name);

}
