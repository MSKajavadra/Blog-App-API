package com.project.blogapp.services;

import com.project.blogapp.payloads.CommentDTO;

public interface CommentService {
    CommentDTO createComment(CommentDTO commentDTO,Integer postId);
    void deleteComment(Integer commentId);
}
