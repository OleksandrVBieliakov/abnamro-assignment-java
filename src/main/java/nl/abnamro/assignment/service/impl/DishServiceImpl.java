package nl.abnamro.assignment.service.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.assignment.dto.*;
import nl.abnamro.assignment.exception.NotFoundException;
import nl.abnamro.assignment.mapper.DishMapper;
import nl.abnamro.assignment.mapper.IngredientMapper;
import nl.abnamro.assignment.model.Dish;
import nl.abnamro.assignment.model.Ingredient;
import nl.abnamro.assignment.model.Product;
import nl.abnamro.assignment.model.Unit;
import nl.abnamro.assignment.repository.DishRepository;
import nl.abnamro.assignment.repository.IngredientRepository;
import nl.abnamro.assignment.service.DishService;
import nl.abnamro.assignment.service.ProductService;
import nl.abnamro.assignment.service.UnitService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

import static nl.abnamro.assignment.model.QDish.dish;
import static nl.abnamro.assignment.model.QIngredient.ingredient;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class DishServiceImpl implements DishService {
    private final IngredientRepository ingredientRepository;
    private final DishRepository dishRepository;
    private final ProductService productService;
    private final UnitService unitService;

    private final DishMapper dishMapper;
    private final IngredientMapper ingredientMapper;

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public DishDto createDish(ShortDishDto dishDto) {
        Dish dish = dishMapper.convert(dishDto);
        dish = saveDish(dish);
        return dishMapper.convert(dish);
    }

    @Override
    public DishDto updateDish(UUID id, ShortDishDto dishDto) {
        Dish dish = getDishEntityById(id);
        dish.setName(dishDto.getName());
        dish.setNumberOfServings(dishDto.getNumberOfServings());
        dish.setInstruction(dishDto.getInstruction());
        dish = saveDish(dish);
        return dishMapper.convert(dish);
    }

    private Dish saveDish(Dish dish) {
        dish.setName(dish.getName().trim());
        return dishRepository.save(dish);
    }

    private Dish getDishEntityById(UUID id) {
        return dishRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Could not find dish with ID " + id));
    }

    @Override
    public DishDto getDishById(UUID id) {
        return dishMapper.convert(getDishEntityById(id));
    }

    @Override
    public Dishes getAllDishes() {
        List<Dish> dishes = dishRepository.findAll();
        return dishMapper.items(dishes);
    }

    @Override
    public IngredientDto addIngredient(UUID id, ShortIngredientDto ingredient) {
        Dish dish = getDishEntityById(id);
        Product product = productService.getProductEntityByName(ingredient.getProduct());
        Unit unit = unitService.getUnitEntityByName(ingredient.getUnit());
        Ingredient ingredientEntity = new Ingredient(null, dish, product, unit, ingredient.getAmount());
        ingredientEntity = ingredientRepository.save(ingredientEntity);
        return ingredientMapper.convert(ingredientEntity);
    }


    @Override
    public void deleteIngredient(UUID id, UUID ingredientId) {
        Dish dish = getDishEntityById(id);
        List<Ingredient> ingredients = dish.getIngredients();
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getId().equals(ingredientId)) {
                ingredientRepository.delete(ingredient);
                return;
            }
        }
        throw new NotFoundException("Given dish doesn't contain given ingredient");
    }

    @Override
    public void deleteDish(UUID id) {
        Dish dish = getDishEntityById(id);
        dishRepository.delete(dish);
    }

    @Override
    public Dishes getDishes(DishFilter filter) {
        log.debug(filter.toString());

        JPAQuery<Dish> query = new JPAQuery<>(entityManager);
        query.from(dish);

        BooleanBuilder bb = new BooleanBuilder();

        String name = filter.getName();
        if (StringUtils.hasText(name))
            bb.and(dish.name.containsIgnoreCase(name));

        Integer numberOfServings = filter.getNumberOfServings();
        if (numberOfServings != null && numberOfServings > 0)
            bb.and(dish.numberOfServings.eq(numberOfServings));

        String instruction = filter.getInstruction();
        if (StringUtils.hasText(instruction))
            bb.and(dish.instruction.containsIgnoreCase(instruction));

        Boolean vegetarian = filter.getVegetarian();
        if (vegetarian != null) {
            JPAQuery<Ingredient> vegetarianQuery = new JPAQuery<>(entityManager);
            vegetarianQuery.from(ingredient)
                .join(ingredient.product)
                .where(ingredient.dish.id.eq(dish.id)
                    .and(ingredient.product.vegetarian.isFalse()));
            if (vegetarian)
                bb.and(vegetarianQuery.notExists());
            else
                bb.and(vegetarianQuery.exists());
        }

        String productName = filter.getProduct();
        if (StringUtils.hasText(productName)) {
            JPAQuery<Ingredient> productQuery = new JPAQuery<>(entityManager);
            productQuery.from(ingredient)
                .join(ingredient.product)
                .where(ingredient.dish.id.eq(dish.id)
                    .and(ingredient.product.name.equalsIgnoreCase(productName)));
            Boolean excludeProduct = filter.getExcludeProduct();
            if (excludeProduct != null && excludeProduct)
                bb.and(productQuery.notExists());
            else
                bb.and(productQuery.exists());
        }

        if (bb.hasValue())
            query.where(bb.getValue());

        List<Dish> dishes = query.fetch();
        return dishMapper.items(dishes);
    }
}
