package com.bulyha.bookstore.service.impl;

import com.bulyha.bookstore.dto.UserDto;
import com.bulyha.bookstore.exception.ResourceNotFoundException;
import com.bulyha.bookstore.model.User;
import com.bulyha.bookstore.repository.UserRepository;
import com.bulyha.bookstore.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public UserDto addUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        User savedUser = userRepository.save(user);
        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public UserDto getUser(Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));
        return modelMapper.map(user.get(), UserDto.class);
    }

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(o -> modelMapper.map(o, UserDto.class)).toList();
    }

    @Override
    public UserDto updateUser(UserDto userDto, Long userId) {
        Optional<User> user = Optional.ofNullable(userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));
        user.ifPresent(u -> u.setId(u.getId()));
        user.ifPresent(u -> u.setFirstName(userDto.getFirstName()));
        user.ifPresent(u -> u.setSurname(userDto.getSurname()));
        userRepository.save(user.get());
        return modelMapper.map(user.get(), UserDto.class);
    }

    @Override
    public void deleteUser(Long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
        } else {
            throw new ResourceNotFoundException("We don't have such user in the database");
        }

    }
}
