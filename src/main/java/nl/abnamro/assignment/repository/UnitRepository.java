package nl.abnamro.assignment.repository;

import nl.abnamro.assignment.model.Dish;
import nl.abnamro.assignment.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Integer>, QuerydslPredicateExecutor<Dish> {
    Optional<Unit> findFirstByNameIgnoreCase(String name);
}
