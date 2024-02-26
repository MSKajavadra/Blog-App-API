package com.project.blogapp.services;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.payloads.PostResponse;


public interface PostService {
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    void deletePost(Integer postId);
    PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    PostDTO getPostById(Integer postId);
    PostResponse getPostByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    PostResponse getPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
    PostResponse searchPost(String keyWord, Integer pageNo, Integer pageSize, String sortBy, String sortDir);
}
