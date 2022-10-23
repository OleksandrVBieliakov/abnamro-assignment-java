package nl.abnamro.assignment.dataloader;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import nl.abnamro.assignment.dto.*;
import nl.abnamro.assignment.mapper.DishMapper;
import nl.abnamro.assignment.service.DishService;
import nl.abnamro.assignment.service.ProductService;
import nl.abnamro.assignment.service.UnitService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataLoader implements CommandLineRunner {
    private final ProductService productService;
    private final UnitService unitService;
    private final DishService dishService;

    private final DishMapper dishMapper;
    private final ObjectMapper objectMapper;

    private String baseDir;

    @FunctionalInterface
    interface Processor {
        void process(File file) throws IOException;
    }

    private void processFile(String fileName, Processor fi) throws Exception {
        File jsonFile = new File(baseDir, fileName);
        if (jsonFile.exists()) {
            fi.process(jsonFile);
        } else
            log.warn("{} not found", jsonFile.getAbsolutePath());
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 1)
            baseDir = args[0];
        else
            baseDir = "data";

        processFile("units.json", (file) -> {
            List<UnitDto> units = objectMapper.readValue(file, Units.class).items();
            for (UnitDto unit : units) {
                unitService.saveUnit(unit);
            }
        });

        processFile("products.json", (file) -> {
            List<ProductDto> products = objectMapper.readValue(file, Products.class).items();
            for (ProductDto product : products) {
                productService.saveProduct(product);
            }
        });

        processFile("dishes.json", (file) -> {
            List<MiddleDishDto> dishes = objectMapper.readValue(file, MiddleDishes.class).items();
            for (MiddleDishDto dish : dishes) {
                UUID id = dishService.createDish(dishMapper.convert(dish)).getId();
                List<IngredientDto> ingredients = dish.getIngredients();
                ingredients.forEach(ingredient -> {
                    String product = ingredient.getProduct().getName();
                    String unit = ingredient.getUnit().getName();
                    Double amount = ingredient.getAmount();
                    ShortIngredientDto ingredientDto = new ShortIngredientDto(product, unit, amount);
                    dishService.addIngredient(id, ingredientDto);
                });
            }
        });
    }
}
