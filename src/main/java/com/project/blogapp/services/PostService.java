package com.project.blogapp.services;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Post;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.repository.CategoryRepo;
import com.project.blogapp.repository.PostRepo;
import com.project.blogapp.repository.UserRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    public PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId){
        Post post=modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId)));
        post.setCategory(categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId)));

        return modelMapper.map(postRepo.save(post),PostDTO.class);
    }

}
