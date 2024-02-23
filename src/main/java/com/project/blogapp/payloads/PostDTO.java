package com.project.blogapp.payloads;

import com.project.blogapp.models.Category;
import com.project.blogapp.models.User;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDTO {
    private int id;
    @NotEmpty
    private String title;
    @NotEmpty
    private String content;
    private String imageName;
    private Date addedDate;
    private CategoryDTO category;
    private UserDTO user;
}
