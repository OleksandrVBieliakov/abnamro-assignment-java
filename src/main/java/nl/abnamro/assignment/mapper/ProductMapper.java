package nl.abnamro.assignment.mapper;

import nl.abnamro.assignment.dto.ProductDto;
import nl.abnamro.assignment.dto.Products;
import nl.abnamro.assignment.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;
import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING, injectionStrategy = CONSTRUCTOR)
public interface ProductMapper {
    @Mapping(target = "id", ignore = true)
    Product convert(ProductDto product);

    ProductDto convert(Product product);

    List<ProductDto> convert(List<Product> products);

    default Products items(List<Product> products) {
        return new Products(convert(products));
    }

}
