package nl.abnamro.assignment.dto;

import java.util.List;

public record Products(
    List<ProductDto> items
) {
}
