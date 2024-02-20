package com.project.blogapp.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {
    private int id;
    @NotNull
    private String name;
    @Email
    private String email;
    @NotEmpty
    private String password;
    @NotEmpty
    private String about;
}
