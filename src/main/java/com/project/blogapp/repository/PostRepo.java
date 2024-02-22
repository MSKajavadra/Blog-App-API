package com.project.blogapp.repository;

import com.project.blogapp.models.Category;
import com.project.blogapp.models.Post;
import com.project.blogapp.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    List<Post> findByUser(User user);
    List<Post> findByCategory(Category category);
}
