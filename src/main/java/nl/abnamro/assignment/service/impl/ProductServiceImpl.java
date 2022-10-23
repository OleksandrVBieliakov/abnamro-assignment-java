package nl.abnamro.assignment.service.impl;

import lombok.RequiredArgsConstructor;
import nl.abnamro.assignment.dto.ProductDto;
import nl.abnamro.assignment.dto.Products;
import nl.abnamro.assignment.exception.NotFoundException;
import nl.abnamro.assignment.mapper.ProductMapper;
import nl.abnamro.assignment.model.Product;
import nl.abnamro.assignment.repository.ProductRepository;
import nl.abnamro.assignment.service.ProductService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    @Override
    public void saveProduct(ProductDto productDto) {
        Product product = productMapper.convert(productDto);
        product.setName(product.getName().toLowerCase());
        productRepository.save(product);
    }

    @Override
    public Product getProductEntityByName(String name) {
        return productRepository.findFirstByNameIgnoreCase(name)
            .orElseThrow(() -> new NotFoundException("Could not find product with name: " + name));
    }

    @Override
    public ProductDto getProductByName(String name) {
        return productMapper.convert(getProductEntityByName(name));
    }

    @Override
    public Products getAllProducts() {
        return productMapper.items(productRepository.findAll());
    }
}
