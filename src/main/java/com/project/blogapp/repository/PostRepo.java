package com.project.blogapp.repository;

import com.project.blogapp.models.Category;
import com.project.blogapp.models.Post;
import com.project.blogapp.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post,Integer> {
    Page<Post> findByUser(User user, Pageable pageable);
    Page<Post> findByCategory(Category category, Pageable pageable);
    Page<Post> findByTitleContaining(String keyWord, Pageable pageable);
}
