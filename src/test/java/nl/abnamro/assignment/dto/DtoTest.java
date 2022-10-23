package nl.abnamro.assignment.dto;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class DtoTest {
    @Test
    void dishFilterToStringTest(){
        DishFilter dishFilter = new DishFilter();
        assertThat(dishFilter.toString(), equalTo(""));

        dishFilter = new DishFilter(null, null, null, true, null, null);
        assertThat(dishFilter.toString(), equalTo("?vegetarian=true"));

        dishFilter = new DishFilter("Ice cream", null, null, false, null, null);
        assertThat(dishFilter.toString(), equalTo("?name=Ice cream&vegetarian=false"));

        dishFilter = new DishFilter("Ice cream", 5, "oven", false, "milk", true);
        assertThat(dishFilter.toString(), equalTo("?name=Ice cream&numberOfServings=5&instruction=oven&vegetarian=false&product=milk&excludeProduct=true"));
    }
}
