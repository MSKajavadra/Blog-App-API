package com.project.blogapp.services.implementations;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Comment;
import com.project.blogapp.models.Post;
import com.project.blogapp.payloads.CommentDTO;
import com.project.blogapp.repository.CommentRepo;
import com.project.blogapp.repository.PostRepo;
import com.project.blogapp.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private PostRepo postRepo;
    @Autowired
    private CommentRepo commentRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CommentDTO createComment(CommentDTO commentDTO, Integer postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post","Id",postId));
        Comment comment=modelMapper.map(commentDTO, Comment.class);
        comment.setPost(post);
        return modelMapper.map(commentRepo.save(comment), CommentDTO.class);
    }

    @Override
    public void deleteComment(Integer commentId) {
        Comment comment=commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment","Id",commentId));
        commentRepo.delete(comment);
    }
}
