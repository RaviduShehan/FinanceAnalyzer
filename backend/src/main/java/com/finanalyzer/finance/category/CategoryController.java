package com.finanalyzer.finance.category;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/create")
    public Category create(@Valid @RequestBody Category category) {
        return categoryService.create(category);
    }
    @GetMapping("/findAll")
    public List<Category> findAll() {
        return categoryService.findAll();
    }
    @GetMapping("{id}")
    public Category findById(@PathVariable Long id){
        return categoryService.findById(id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}