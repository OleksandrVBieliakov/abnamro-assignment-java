package nl.abnamro.assignment.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UnitDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
}
