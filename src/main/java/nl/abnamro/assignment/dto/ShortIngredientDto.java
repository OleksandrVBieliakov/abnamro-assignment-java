package nl.abnamro.assignment.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortIngredientDto {
    @NotNull(message = "product is mandatory")
    private String product;
    @NotNull(message = "unit is mandatory")
    private String unit;
    @NotNull(message = "amount is mandatory")
    private Double amount;
}
