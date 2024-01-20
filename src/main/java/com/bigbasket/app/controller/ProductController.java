package com.bigbasket.app.controller;

import com.bigbasket.app.dto.ProductDto;
import com.bigbasket.app.model.Category;
import com.bigbasket.app.model.common.ApiResponse;
import com.bigbasket.app.service.CategoryService;
import com.bigbasket.app.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(
            CategoryService categoryService,
            ProductService productService
    ) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createProduct(@RequestBody ProductDto dto) {
        Optional<Category> category = categoryService.findById(dto.getCategoryId());

        if(!category.isPresent()) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            false,
                            "This category does not exist"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } else {
            productService.createProduct(dto, category.get());

            return new ResponseEntity<>(
                    new ApiResponse(
                            true,
                            "A new product has been created"
                    ),
                    HttpStatus.CREATED
            );
        }
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = productService.getProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @PutMapping("/update/{productId}")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable("productId") Integer productId,
            @RequestBody ProductDto dto
    ) {
        if (!productService.existsById(productId)) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            false,
                            "This product does not exist"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } else if(!categoryService.existsById(dto.getCategoryId())) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            false,
                            "This category does not exist"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        } else {
            productService.updateProduct(productId, dto);
            return new ResponseEntity<>(
                    new ApiResponse(
                            true,
                            "Product has been updated"
                    ),
                    HttpStatus.OK
            );
        }
    }
}
