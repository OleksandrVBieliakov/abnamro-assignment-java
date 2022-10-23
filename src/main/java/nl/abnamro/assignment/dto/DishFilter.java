package nl.abnamro.assignment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.StringUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DishFilter {
    private String name;
    private Integer numberOfServings;
    private String instruction;
    private Boolean vegetarian;
    private String product;
    private Boolean excludeProduct;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.hasText(name))
            sb.append("&name=").append(name);
        if (numberOfServings != null && numberOfServings > 0)
            sb.append("&numberOfServings=").append(numberOfServings);
        if (StringUtils.hasText(instruction))
            sb.append("&instruction=").append(instruction);
        if (vegetarian != null)
            sb.append("&vegetarian=").append(vegetarian);
        if (StringUtils.hasText(product))
            sb.append("&product=").append(product);
        if (excludeProduct != null)
            sb.append("&excludeProduct=").append(excludeProduct);
        if (!sb.isEmpty())
            sb.replace(0,1, "?");
        return sb.toString();
    }
}
