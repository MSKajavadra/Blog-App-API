package com.project.blogapp.services.implementations;

import com.project.blogapp.config.AppConstants;
import com.project.blogapp.exceptions.ResourceNotFoundException;
import com.project.blogapp.models.Role;
import com.project.blogapp.models.User;
import com.project.blogapp.payloads.UserDTO;
import com.project.blogapp.repository.RoleRepo;
import com.project.blogapp.repository.UserRepo;
import com.project.blogapp.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDTO registerUser(UserDTO userDTO) {
        User user=modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = roleRepo.findById(AppConstants.NORMAL_USER).get();
        user.getRoles().add(role);
        return modelMapper.map(userRepo.save(user),UserDTO.class);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO){
        User user = modelMapper.map(userDTO,User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return modelMapper.map(userRepo.save(user),UserDTO.class);
    }
    @Override
    public UserDTO updateUser(UserDTO userDTO,Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAbout(userDTO.getAbout());

        return modelMapper.map(userRepo.save(user),UserDTO.class);
    }
    @Override
    public UserDTO getUserById(Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));

        return modelMapper.map(user,UserDTO.class);
    }
    @Override
    public List<UserDTO> getAllUsers(){
        List<UserDTO> userDTOS=new ArrayList<>();
        for(User user:userRepo.findAll()){
            userDTOS.add(modelMapper.map(user,UserDTO.class));
        }
        return userDTOS;
    }
    @Override
    public void deleteUser(Integer userId){
        User user=userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user","Id",userId));
        userRepo.delete(user);
    }
}
