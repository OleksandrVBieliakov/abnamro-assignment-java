package nl.abnamro.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShortDishDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    @NotNull(message = "NumberOfServings is mandatory")
    private Integer numberOfServings;
    private String instruction;
}
