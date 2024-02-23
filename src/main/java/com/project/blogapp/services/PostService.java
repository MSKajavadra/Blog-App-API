package com.project.blogapp.services;
import com.project.blogapp.payloads.PostDTO;
import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO, Integer userId, Integer categoryId);
    PostDTO updatePost(PostDTO postDTO,Integer postId);
    void deletePost(Integer postId);
    List<PostDTO> getAllPost();
    PostDTO getPostById(Integer postId);
    List<PostDTO> getPostByCategory(Integer categoryId);
    List<PostDTO> getPostByUser(Integer userId);
}
