package nl.abnamro.assignment.dto;

import lombok.Data;

import java.util.List;

@Data
public class MiddleDishDto {
    private String name;
    private Integer numberOfServings;
    private String instruction;
    private List<IngredientDto> ingredients;
}
