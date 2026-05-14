package com.finanalyzer.finance.category;

import com.finanalyzer.finance.category.dto.*;
import com.finanalyzer.finance.common.exception.DuplicateResourceException;
import com.finanalyzer.finance.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponseDTO create(CreateCategoryRequestDTO request) {
        if (categoryRepository.existsByNameIgnoreCase(request.name())) {
            throw new DuplicateResourceException("Category already exists with name: " + request.name());
        }

        Category category = new Category();
        category.setName(request.name());
        category.setType(request.type());

        Category savedCategory = categoryRepository.save(category);
        return toResponse(savedCategory);
    }

    public List<CategoryResponseDTO> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponseDTO findById(Long id) {
        Category category = getCategoryOrThrow(id);
        return toResponse(category);
    }

    public CategoryResponseDTO update(Long id, UpdateCategoryRequestDTO request) {
        Category category = getCategoryOrThrow(id);

        category.setName(request.name());
        category.setType(request.type());

        Category updatedCategory = categoryRepository.save(category);
        return toResponse(updatedCategory);
    }

    public void deleteById(Long id) {
        Category category = getCategoryOrThrow(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryOrThrow(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Category not found with id: " + id
                ));
    }

    private CategoryResponseDTO toResponse(Category category) {
        return new CategoryResponseDTO(
                category.getId(),
                category.getName(),
                category.getType(),
                category.getCreatedAt()
        );
    }
}