package nl.abnamro.assignment.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class DishDto {
    private UUID id;
    private String name;
    private Integer numberOfServings;
    private String instruction;
    private Boolean vegetarian;
    private List<IngredientDto> ingredients;

    //if all ingredients of the dish are vegetarian then dish is vegetarian as well
    public Boolean getVegetarian() {
        return ingredients.stream().allMatch(ingredient -> ingredient.getProduct().getVegetarian());
    }
}
