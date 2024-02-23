package com.project.blogapp.services.impl;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Category;
import com.project.blogapp.models.Post;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.payloads.UserDTO;
import com.project.blogapp.repository.CategoryRepo;
import com.project.blogapp.repository.PostRepo;
import com.project.blogapp.repository.UserRepo;
import com.project.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @Override
    public PostDTO createPost(PostDTO postDTO,Integer userId,Integer categoryId){
        Post post=modelMapper.map(postDTO,Post.class);
        post.setImageName("default.png");
        post.setAddedDate(new Date());
        post.setUser(userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId)));
        post.setCategory(categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId)));

        return modelMapper.map(postRepo.save(post),PostDTO.class);
    }
    @Override
    public PostDTO updatePost(PostDTO postDTO,Integer postId){
        Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setImageName(postDTO.getImageName());

        return modelMapper.map(postRepo.save(post), PostDTO.class);
    }
    @Override
    public void deletePost(Integer postId){
        Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        postRepo.delete(post);
    }
    @Override
    public List<PostDTO> getAllPost(){
        List<PostDTO> postDTOS=new ArrayList<>();
        for(Post post:postRepo.findAll()){
            postDTOS.add(modelMapper.map(post,PostDTO.class));
        }
        return postDTOS;
    }
    @Override
    public PostDTO getPostById(Integer postId){
        Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        return modelMapper.map(post,PostDTO.class);
    }
    @Override
    public List<PostDTO> getPostByCategory(Integer categoryId){
        Category category=categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));
        List<Post> posts=postRepo.findByCategory(category);
        return posts.stream().map(post -> modelMapper.map(post,PostDTO.class)).collect(Collectors.toList());
    }
    @Override
    public List<PostDTO> getPostByUser(Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));
        List<PostDTO> postDTOS=new ArrayList<>();
        for(Post post : postRepo.findByUser(user)){
            postDTOS.add(modelMapper.map(post,PostDTO.class));
        }
        return postDTOS;
    }
}
