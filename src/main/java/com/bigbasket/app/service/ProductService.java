package com.bigbasket.app.service;

import com.bigbasket.app.dto.ProductDto;
import com.bigbasket.app.exception.ProductDoesNotExistException;
import com.bigbasket.app.model.Category;
import com.bigbasket.app.model.Product;
import com.bigbasket.app.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductDto getProductDto(Product product) {
        return new ProductDto(
                product.getId(),
                product.getName(),
                product.getImageURL(),
                product.getPrice(),
                product.getDescription(),
                product.getCategory().getId()
        );
    }

    public void createProduct(ProductDto dto, Category category) {
        Product product = new Product(
                dto.getName(),
                dto.getImageURL(),
                dto.getPrice(),
                dto.getDescription(),
                category
        );

        repository.save(product);
    }

    public List<ProductDto> getProducts() {
        List<Product> products = repository.findAll();
        List<ProductDto> dtoList = new ArrayList<>();

        for(Product product: products) {
            dtoList.add(getProductDto(product));
        }

        return dtoList;
    }

    public boolean existsById(Integer id) {
        return repository.existsById(id);
    }

    public ProductDto updateProduct(Integer productId, ProductDto updatedProduct) {
        Optional<Product> product = repository.findById(productId);

        if(!product.isPresent()) {
            throw new NullPointerException("This product does not exist");
        } else {
            product.get().setName(updatedProduct.getName());
            product.get().setImageURL(updatedProduct.getImageURL());
            product.get().setPrice(updatedProduct.getPrice());
            product.get().setDescription(updatedProduct.getDescription());

            Product savedProduct = repository.save(product.get());
            return getProductDto(savedProduct);
        }
    }

    public Product findById(Integer productId) throws ProductDoesNotExistException {
        Optional<Product> optionalProduct = repository.findById(productId);

        if (optionalProduct.isEmpty()) {
            throw new ProductDoesNotExistException("product id is invalid: " + productId);
        }

        return optionalProduct.get();
    }
}
