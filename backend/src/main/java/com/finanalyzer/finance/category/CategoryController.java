package com.finanalyzer.finance.category;

import com.finanalyzer.finance.category.dto.*;
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
    public CategoryResponseDTO create(@Valid @RequestBody CreateCategoryRequestDTO request) {
        return categoryService.create(request);
    }

    @GetMapping("/findAll")
    public List<CategoryResponseDTO> findAll() {
        return categoryService.findAll();
    }

    @GetMapping("/{id}")
    public CategoryResponseDTO findById(@PathVariable Long id) {
        return categoryService.findById(id);
    }

    @PutMapping("/edit/{id}")
    public CategoryResponseDTO update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequestDTO request
    ) {
        return categoryService.update(id, request);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        categoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}