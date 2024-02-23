package com.project.blogapp.services.impl;

import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.UserDTO;
import com.project.blogapp.repository.UserRepo;
import com.project.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public UserDTO createUser(UserDTO userDTO){
        User user = userRepo.save(dtoToUser(userDTO));
        return userToDTO(user);
    }
    @Override
    public UserDTO updateUser(UserDTO userDTO,Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setAbout(userDTO.getAbout());

        return userToDTO(userRepo.save(user));
    }
    @Override
    public UserDTO getUserById(Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));

        return userToDTO(user);
    }
    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDTO> userDTOS=new ArrayList<>();
        for(User user:userRepo.findAll()){
            userDTOS.add(userToDTO(user));
        }
        return userDTOS;
    }
    @Override
    public void deleteUser(Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));
        userRepo.delete(user);
    }

    public User dtoToUser(UserDTO userDTO){
//        return new User(userDTO.getId(),userDTO.getName(),userDTO.getEmail(),userDTO.getPassword(),userDTO.getAbout());
        return modelMapper.map(userDTO,User.class);
    }
    public UserDTO userToDTO(User user){
//        return new UserDTO(user.getId(), user.getName(), user.getEmail(),user.getPassword(),user.getAbout());
        return modelMapper.map(user,UserDTO.class);
    }
}
