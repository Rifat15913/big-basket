package com.bigbasket.app.controller;

import com.bigbasket.app.model.Category;
import com.bigbasket.app.model.common.ApiResponse;
import com.bigbasket.app.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponse> createCategory(@RequestBody Category category) {
        categoryService.createCategory(category);

        return new ResponseEntity<>(
                new ApiResponse(
                        true,
                        "A new category has been created"
                ),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/")
    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    @PutMapping("/update/{categoryId}")
    public ResponseEntity<ApiResponse> updateCategory(
            @PathVariable("categoryId") Integer categoryId,
            @RequestBody Category category
    ) {
        if (!categoryService.existsById(categoryId)) {
            return new ResponseEntity<>(
                    new ApiResponse(
                            false,
                            "This category does not exist"
                    ),
                    HttpStatus.BAD_REQUEST
            );
        }

        categoryService.updateCategory(categoryId, category);

        return new ResponseEntity<>(
                new ApiResponse(
                        true,
                        "Category has been updated"
                ),
                HttpStatus.OK
        );
    }
}
