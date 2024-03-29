package com.example.GestioneContocorrente.service;

import com.example.GestioneContocorrente.dtos.UserDtoRequest;
import com.example.GestioneContocorrente.dtos.UserDtoResponse;
import com.example.GestioneContocorrente.mappers.Mapper;
import com.example.GestioneContocorrente.model.User;
import com.example.GestioneContocorrente.repository.UserRepo;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class UserServiceImpl implements UserService{
    private final Mapper<User, UserDtoResponse> mapper;
    public final UserRepo userRepo;

    public UserServiceImpl(@Qualifier("userMapper")  Mapper<User, UserDtoResponse> mapper, UserRepo userRepo) {
        this.mapper = mapper;
        this.userRepo = userRepo;
    }

    @Override
    public List<UserDtoResponse> getAllUsers() {
        List<User> userList = userRepo.findAll();
        return mapper.mapAll(userList);
    }

    @Override
    public UserDtoResponse createUser(UserDtoRequest userDtoRequest) {
        User user = User.builder()
                .firstname(userDtoRequest.getFirstname())
                .lastname(userDtoRequest.getLastname())
                .ssn(userDtoRequest.getSsn())
                .username(userDtoRequest.getUsername())
                .password(userDtoRequest.getPassword())
                .createdAt(LocalDateTime.now())
                .build();
        userRepo.save(user);
        return  mapper.map(user);
    }

    @Override
    public UserDtoResponse getUserById(Long id) throws Exception {
       User user = userRepo.getUserById(id).orElseThrow(()-> new Exception("User not found"));
       return mapper.map(user);
    }

    @Override
    public void deleteUser(Long id) {
    userRepo.deleteById(id);
    }
}
