package nl.abnamro.assignment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import nl.abnamro.assignment.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FullScenarioTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void playScenario() throws Exception {
        testCreation();
        testUpdating();
        testFiltering();
        testDeletion();
    }

    private DishDto createDish(ShortDishDto dish) throws Exception {
        String content = mockMvc.perform(post("/dish")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dish)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, DishDto.class);
    }

    private DishDto updateDish(UUID id, ShortDishDto dish) throws Exception {
        String content = mockMvc.perform(patch("/dish/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(dish)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, DishDto.class);
    }

    @SuppressWarnings("UnusedReturnValue")
    private IngredientDto addIngredient(UUID id, ShortIngredientDto ingredient) throws Exception {
        String content = mockMvc.perform(post("/dish/{id}/ingredient", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(ingredient)))
            .andExpect(status().isCreated())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, IngredientDto.class);
    }

    private List<DishDto> getDishes() throws Exception {
        String content = mockMvc.perform(get("/dish"))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, Dishes.class).items();
    }

    private DishDto getDish(UUID id) throws Exception {
        String content = mockMvc.perform(get("/dish/{id}", id))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, DishDto.class);
    }

    private List<DishDto> filterDishes(DishFilter filter) throws Exception {
        String content = mockMvc.perform(get("/dish/filter" + filter))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();
        return mapper.readValue(content, Dishes.class).items();
    }

    private void deleteIngredient(UUID id, UUID ingredientId) throws Exception {
        mockMvc.perform(delete("/dish/{id}/ingredient/{ingredientId}", id, ingredientId))
            .andExpect(status().isOk());
    }

    private void deleteDish(UUID id) throws Exception {
        mockMvc.perform(delete("/dish/{id}", id))
            .andExpect(status().isOk());
    }

    //create 2 dishes
    private void testCreation() throws Exception {
        List<DishDto> dishes = getDishes();

        int dishesSize = dishes.size();

        ShortDishDto shortDishDto = new ShortDishDto("Salmon with rice", 2,
            "Slice salmon. Rinse rice. Place rice into oven...");

        List<ShortIngredientDto> ingredients = List.of(
            new ShortIngredientDto("salmon", "gram", 200.0),
            new ShortIngredientDto("salt", "teaspoon", 1.0)
        );

        createDishAndAddIngredients(shortDishDto, ingredients);

        shortDishDto = new ShortDishDto("Backed salmon", 2,
            "Slice lemon. Put lemon and salt inside salmon. Wrap salmon with foil and place into into oven for 15 minutes");

        ingredients = List.of(
            new ShortIngredientDto("salmon", "gram", 200.0),
            new ShortIngredientDto("lemon", "piece", 1.0),
            new ShortIngredientDto("salt", "teaspoon", 3.0)
        );

        createDishAndAddIngredients(shortDishDto, ingredients);

        dishes = getDishes();
        int dishesSizeAfterDelete = dishes.size();
        assertThat(dishesSizeAfterDelete, equalTo(dishesSize + 2));

    }

    private void testUpdating() throws Exception {
        List<DishDto> dishes = getDishes();
        DishDto dish = dishes.get(0);
        UUID id = dish.getId();
        Integer numberOfServings = dish.getNumberOfServings();

        ShortDishDto shortDishDto = new ShortDishDto(dish.getName(), numberOfServings + 1, dish.getInstruction());

        dish = updateDish(id, shortDishDto);

        Integer numberOfServingsAfterUpdate = dish.getNumberOfServings();

        assertThat(numberOfServingsAfterUpdate, equalTo(numberOfServings + 1));
    }

    private void testFiltering() throws Exception {
        List<DishDto> allDishes = getDishes();
        int totalSize = allDishes.size();

        //empty filter should give the same result as get all dishes
        List<DishDto>  dishes = filterDishes(new DishFilter());
        assertThat(dishes.size(), equalTo(totalSize));

        //All vegetarian recipes
        dishes = filterDishes(new DishFilter(null, null, null, true, null, null));
        int vegetarianSize = dishes.size();
        //All non vegetarian recipes
        dishes = filterDishes(new DishFilter(null, null, null, false, null, null));
        int nonVegetarianSize = dishes.size();
        assertThat(vegetarianSize + nonVegetarianSize, equalTo(totalSize));

        //get dishes with number of servings 2
        int count = (int) allDishes.stream().filter(dish -> dish.getNumberOfServings().equals(2)).count();
        dishes = filterDishes(new DishFilter(null, 2, null, null, null, null));
        assertThat(dishes.size(), equalTo(count));

        //Recipes that can serve 4 persons and have "potatoes" as an ingredient
        count = (int) allDishes.stream().filter(dish -> dish.getNumberOfServings().equals(4)
            && dish.getIngredients().stream().anyMatch(ingredient -> ingredient.getProduct().getName().equalsIgnoreCase("potatoes"))).count();
        dishes = filterDishes(new DishFilter(null, 4, null, null, "potatoes", null));
        assertThat(dishes.size(), equalTo(count));

        //Recipes without "salmon" as an ingredient that has "oven" in the instructions
        count = (int) allDishes.stream().filter(dish -> dish.getInstruction() != null && dish.getInstruction().toLowerCase().contains("oven")
            && dish.getIngredients().stream().noneMatch(ingredient -> ingredient.getProduct().getName().equalsIgnoreCase("salmon"))).count();
        dishes = filterDishes(new DishFilter(null, 4, "oven", null, "salmon", true));
        assertThat(dishes.size(), equalTo(count));

    }

    private void createDishAndAddIngredients(ShortDishDto shortDishDto, List<ShortIngredientDto> ingredients) throws Exception {
        DishDto dish = createDish(shortDishDto);
        UUID id = dish.getId();
        for (ShortIngredientDto ingredient : ingredients) {
            addIngredient(id, ingredient);
        }
    }

    //test deletion of one ingredient and the whole dish
    private void testDeletion() throws Exception {
        List<DishDto> dishes = getDishes();

        int dishesSize = dishes.size();

        DishDto dish = dishes.stream().filter(it -> it.getName().equals("Scrambled eggs")).findFirst().orElseThrow();

        List<IngredientDto> ingredients = dish.getIngredients();
        int ingredientsSize = ingredients.size();

        IngredientDto ingredient = ingredients.stream().filter(it -> it.getProduct().getName().equals("milk")).findFirst().orElseThrow();

        UUID id = dish.getId();
        UUID ingredientId = ingredient.getId();

        deleteIngredient(id, ingredientId);

        dish = getDish(id);
        int ingredientsSizeAfterDelete = dish.getIngredients().size();
        assertThat(ingredientsSizeAfterDelete, equalTo(ingredientsSize - 1));

        deleteDish(id);

        dishes = getDishes();
        int dishesSizeAfterDelete = dishes.size();
        assertThat(dishesSizeAfterDelete, equalTo(dishesSize - 1));
    }
}
