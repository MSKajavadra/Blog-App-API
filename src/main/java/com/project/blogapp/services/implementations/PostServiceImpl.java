package com.project.blogapp.services.implementations;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Category;
import com.project.blogapp.models.Post;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.PostDTO;
import com.project.blogapp.payloads.PostResponse;
import com.project.blogapp.repository.CategoryRepo;
import com.project.blogapp.repository.PostRepo;
import com.project.blogapp.repository.UserRepo;
import com.project.blogapp.services.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public PostResponse getAllPost(Integer pageNo, Integer pageSize, String sortBy, String sortDir){
        Sort sort=Sort.by(sortBy);
        if(sortDir.equalsIgnoreCase("desc")) sort = sort.descending();

        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        Page<Post> postPage=postRepo.findAll(pageable);

        List<PostDTO> postDTOS=new ArrayList<>();
        for(Post post:postPage.getContent()){
            postDTOS.add(modelMapper.map(post,PostDTO.class));
        }

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(postPage.getNumber()+1);
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }
    @Override
    public PostDTO getPostById(Integer postId){
        Post post=postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        return modelMapper.map(post,PostDTO.class);
    }
    @Override
    public PostResponse getPostByCategory(Integer categoryId, Integer pageNo, Integer pageSize, String sortBy, String sortDir){
        Category category=categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category","Id",categoryId));

        Sort sort=Sort.by(sortBy);
        if(sortDir.equalsIgnoreCase("desc")) sort = sort.descending();

        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        Page<Post> postPage=postRepo.findByCategory(category,pageable);
        List<PostDTO> postDTOS=postPage.stream().map(post -> modelMapper.map(post,PostDTO.class)).toList();

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(postPage.getNumber()+1);
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }
    @Override
    public PostResponse getPostByUser(Integer userId, Integer pageNo, Integer pageSize, String sortBy, String sortDir){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","Id",userId));

        Sort sort=Sort.by(sortBy);
        if(sortDir.equalsIgnoreCase("desc")) sort = sort.descending();

        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        Page<Post> postPage=postRepo.findByUser(user,pageable);
        List<PostDTO> postDTOS=postPage.stream().map(post -> modelMapper.map(post,PostDTO.class)).toList();

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(postPage.getNumber()+1);
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }

    @Override
    public PostResponse searchPost(String keyWord, Integer pageNo, Integer pageSize, String sortBy, String sortDir) {
        Sort sort=Sort.by(sortBy);
        if(sortDir.equalsIgnoreCase("desc")) sort = sort.descending();

        Pageable pageable= PageRequest.of(pageNo-1,pageSize,sort);
        Page<Post> postPage=postRepo.findByTitleContaining(keyWord,pageable);

        List<PostDTO> postDTOS=postPage.stream().map(post -> modelMapper.map(post,PostDTO.class)).toList();

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(postDTOS);
        postResponse.setPageNo(postPage.getNumber()+1);
        postResponse.setPageSize(postPage.getSize());
        postResponse.setTotalElements(postPage.getTotalElements());
        postResponse.setTotalPage(postPage.getTotalPages());
        postResponse.setLastPage(postPage.isLast());

        return postResponse;
    }
}
