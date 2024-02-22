package com.project.blogapp.services;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Category;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.CategoryDTO;
import com.project.blogapp.payloads.UserDTO;
import com.project.blogapp.repository.CategoryRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    public CategoryDTO createCategory(CategoryDTO categoryDTO){
        Category category=modelMapper.map(categoryDTO, Category.class);
        return modelMapper.map(categoryRepo.save(category),CategoryDTO.class);
    }
    public CategoryDTO updateCategory(CategoryDTO categoryDTO,Integer categoryId){
        Category category=categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        category.setCategoryTitle(categoryDTO.getCategoryTitle());
        category.setCategoryDescription(category.getCategoryDescription());

        return modelMapper.map(categoryRepo.save(category),CategoryDTO.class);
    }
    public void deleteCategory(Integer categoryId){
        Category category=categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        categoryRepo.delete(category);
    }
    public CategoryDTO getCategoryById(Integer categoryId){
        Category category=categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        return modelMapper.map(category,CategoryDTO.class);
    }
    public List<CategoryDTO> getAllCategory(){
        List<CategoryDTO> categoryDTOS=new ArrayList<>();
        for(Category category:categoryRepo.findAll()){
            categoryDTOS.add(modelMapper.map(category,CategoryDTO.class));
        }
        return categoryDTOS;
    }
}
