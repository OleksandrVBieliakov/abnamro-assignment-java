package nl.abnamro.assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IngredientDto {
    private UUID id;
    private ProductDto product;
    private UnitDto unit;
    private Double amount;
}
