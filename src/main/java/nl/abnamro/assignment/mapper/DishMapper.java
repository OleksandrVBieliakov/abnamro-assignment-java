package nl.abnamro.assignment.mapper;

import nl.abnamro.assignment.dto.DishDto;
import nl.abnamro.assignment.dto.Dishes;
import nl.abnamro.assignment.dto.MiddleDishDto;
import nl.abnamro.assignment.dto.ShortDishDto;
import nl.abnamro.assignment.model.Dish;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR, uses = {IngredientMapper.class})
public interface DishMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ingredients", ignore = true)
    Dish convert(ShortDishDto dish);
    @Mapping(target = "vegetarian", ignore = true)
    DishDto convert(Dish dish);
    List<DishDto> convert(List<Dish> dishes);
    default Dishes items(List<Dish> dishes) {
        return new Dishes(convert(dishes));
    }

    ShortDishDto convert(MiddleDishDto dish);

}
