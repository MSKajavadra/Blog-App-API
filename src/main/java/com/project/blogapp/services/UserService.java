package com.project.blogapp.services;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.UserDTO;

import java.util.ArrayList;
import java.util.List;

public interface UserService {
    UserDTO registerUser(UserDTO userDTO);
    UserDTO createUser(UserDTO userDTO);
    UserDTO updateUser(UserDTO userDTO, Integer userId);
    UserDTO getUserById(Integer userId);
    List<UserDTO> getAllUsers();
    void deleteUser(Integer userId);
}
