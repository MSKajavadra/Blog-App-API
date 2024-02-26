package com.project.blogapp.controllers;

import com.project.blogapp.payloads.ApiResponse;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.payloads.PostResponse;
import com.project.blogapp.services.PostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/blog")
public class PostController {
    @Autowired
    private PostService postService;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.createPost(postDTO,userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNo",defaultValue = "1",required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                                       @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return new ResponseEntity<>(postService.getPostByUser(userId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNo",defaultValue = "1",required = false) Integer pageNo,
                                                           @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                                           @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return new ResponseEntity<>(postService.getPostByCategory(categoryId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue = "1",required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                                   @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return new ResponseEntity<>(postService.getAllPost(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
    @GetMapping("/post/{postId}")
    public ResponseEntity<PostDTO> getPostById(@PathVariable Integer postId){
        return new ResponseEntity<>(postService.getPostById(postId),HttpStatus.OK);
    }

    @DeleteMapping("/post/{postId}")
    public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId){
        postService.deletePost(postId);
        return new ResponseEntity<>(new ApiResponse("Post Deleted Successfully",true),HttpStatus.OK);
    }

    @PutMapping("/post/{postId}")
    public ResponseEntity<PostDTO> updatePost(@Valid@RequestBody PostDTO postDTO,@PathVariable Integer postId){
        return new ResponseEntity<>(postService.updatePost(postDTO,postId),HttpStatus.OK);
    }

    @GetMapping("/post/search/{keyword}")
    public ResponseEntity<PostResponse> searchPostByTitle(@PathVariable String keyword,
                                                          @RequestParam(value = "pageNo",defaultValue = "1",required = false) Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = "10",required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = "id",required = false) String sortBy,
                                                          @RequestParam(value = "sortDir",defaultValue = "asc",required = false) String sortDir){
        return new ResponseEntity<>(postService.searchPost(keyword,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }
}