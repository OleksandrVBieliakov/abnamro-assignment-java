package nl.abnamro.assignment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class ProductDto {
    @NotBlank(message = "Name is mandatory")
    private String name;

    private Boolean vegetarian;
}
