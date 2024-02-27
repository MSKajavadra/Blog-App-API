package com.project.blogapp.controllers;

import com.project.blogapp.config.AppConstants;
import com.project.blogapp.payloads.ApiResponse;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.payloads.PostResponse;
import com.project.blogapp.services.FileService;
import com.project.blogapp.services.PostService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/blog")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/user/{userId}/category/{categoryId}/post")
    public ResponseEntity<PostDTO> createPost(@Valid @RequestBody PostDTO postDTO,
                                              @PathVariable Integer userId,
                                              @PathVariable Integer categoryId){
        return new ResponseEntity<>(postService.createPost(postDTO,userId,categoryId), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/post")
    public ResponseEntity<PostResponse> getPostsByUser(@PathVariable Integer userId,
                                                       @RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false) Integer pageNo,
                                                       @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                       @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                       @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(postService.getPostByUser(userId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/post")
    public ResponseEntity<PostResponse> getPostsByCategory(@PathVariable Integer categoryId,
                                                           @RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false) Integer pageNo,
                                                           @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                           @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                           @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(postService.getPostByCategory(categoryId,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/post")
    public ResponseEntity<PostResponse> getAllPost(@RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false) Integer pageNo,
                                                   @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                   @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                   @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
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
                                                          @RequestParam(value = "pageNo",defaultValue = AppConstants.PAGE_NO,required = false) Integer pageNo,
                                                          @RequestParam(value = "pageSize",defaultValue = AppConstants.PAGE_SIZE,required = false) Integer pageSize,
                                                          @RequestParam(value = "sortBy",defaultValue = AppConstants.SORT_BY,required = false) String sortBy,
                                                          @RequestParam(value = "sortDir",defaultValue = AppConstants.SORT_DIR,required = false) String sortDir){
        return new ResponseEntity<>(postService.searchPost(keyword,pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @PostMapping("/post/image/upload/{postId}")
    public ResponseEntity<PostDTO> uploadPostImage(@RequestParam("image")MultipartFile image,
                                                         @PathVariable Integer postId) throws IOException {
        PostDTO postDTO = postService.getPostById(postId);
        String fileName = fileService.uploadImage(path, image);
        postDTO.setImageName(fileName);
        postService.updatePost(postDTO, postId);

        return new ResponseEntity<>(postDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/post/image/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void downloadImage(@PathVariable String imageName,
                              HttpServletResponse response) throws IOException {
        InputStream resource = fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }
}