package com.finanalyzer.finance.category;

import com.finanalyzer.finance.common.exception.ResourceNotFoundException;import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category create(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }
    public Category findById(Long id){
        return categoryRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Category not found for the id: "+ id)
        );
    }
    public void deleteById(Long id) {
        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Category not found with id: " + id
            );
        }

        categoryRepository.deleteById(id);
    }
}