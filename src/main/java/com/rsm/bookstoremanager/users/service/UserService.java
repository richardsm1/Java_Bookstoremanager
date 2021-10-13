package com.rsm.bookstoremanager.users.service;

import com.rsm.bookstoremanager.users.dto.MessageDTO;
import com.rsm.bookstoremanager.users.dto.UserDTO;
import com.rsm.bookstoremanager.users.entity.User;
import com.rsm.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.rsm.bookstoremanager.users.mapper.UserMapper;
import com.rsm.bookstoremanager.users.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        String createdUserMessage = String.format("User %s with ID %s successfully created", createdUser.getUsername(), createdUser.getId());
        return MessageDTO.builder().message(createdUserMessage).build();
    }

    private void verifyIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }


}
