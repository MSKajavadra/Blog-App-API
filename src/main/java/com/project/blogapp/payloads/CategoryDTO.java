package com.project.blogapp.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CategoryDTO {

    private int categoryId;

    @NotEmpty
    @Size(min = 4)
    private String categoryTitle;

    @NotEmpty
    @Size(min = 10)
    private String categoryDescription;
}
