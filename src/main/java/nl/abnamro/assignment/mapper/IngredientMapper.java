package nl.abnamro.assignment.mapper;

import nl.abnamro.assignment.dto.IngredientDto;
import nl.abnamro.assignment.model.Ingredient;
import org.mapstruct.Mapper;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR, uses = {ProductMapper.class, UnitMapper.class})
public interface IngredientMapper {
    IngredientDto convert(Ingredient ingredient);
}
