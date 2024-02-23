package com.project.blogapp.services;

import com.project.blogapp.payloads.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public CategoryDTO createCategory(CategoryDTO categoryDTO);
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId);
    public void deleteCategory(Integer categoryId);
    public CategoryDTO getCategoryById(Integer categoryId);
    public List<CategoryDTO> getAllCategory();
}
