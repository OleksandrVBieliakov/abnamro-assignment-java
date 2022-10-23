package nl.abnamro.assignment.service;

import nl.abnamro.assignment.dto.ProductDto;
import nl.abnamro.assignment.dto.Products;
import nl.abnamro.assignment.model.Product;

public interface ProductService {

    void saveProduct(ProductDto product);

    Product getProductEntityByName(String name);

    ProductDto getProductByName(String name);

    Products getAllProducts();

}
