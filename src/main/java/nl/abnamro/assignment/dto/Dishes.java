package nl.abnamro.assignment.dto;

import java.util.List;

public record Dishes(
    List<DishDto> items
) {
}
