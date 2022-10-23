package nl.abnamro.assignment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.dto.ProductDto;
import nl.abnamro.assignment.dto.Products;
import nl.abnamro.assignment.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "Products",
    description = "API for products")
@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController extends BaseController {
    private final ProductService productService;

    @Operation(summary = "Create product")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    void saveProduct(@Valid @RequestBody ProductDto product) {
        productService.saveProduct(product);
    }

    @Operation(summary = "Get the product by name. Names of products are unique")
    @GetMapping("/{name}")
    ProductDto getProductByName(@PathVariable String name) {
        return productService.getProductByName(name);
    }

    @Operation(summary = "Get all products")
    @GetMapping
    Products getAllProducts() {
        return productService.getAllProducts();
    }

}
