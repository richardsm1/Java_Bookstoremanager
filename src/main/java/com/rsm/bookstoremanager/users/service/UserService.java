package com.rsm.bookstoremanager.users.service;

import com.rsm.bookstoremanager.users.dto.MessageDTO;
import com.rsm.bookstoremanager.users.dto.UserDTO;
import com.rsm.bookstoremanager.users.entity.User;
import com.rsm.bookstoremanager.users.exception.UserAlreadyExistsException;
import com.rsm.bookstoremanager.users.exception.UserNotFoundException;
import com.rsm.bookstoremanager.users.mapper.UserMapper;
import com.rsm.bookstoremanager.users.repository.UserRepository;
import com.rsm.bookstoremanager.users.utils.MessageDTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rsm.bookstoremanager.users.utils.MessageDTOUtils.creationMessage;
import static com.rsm.bookstoremanager.users.utils.MessageDTOUtils.updatedMessage;

@Service
public class UserService {

    private final static UserMapper userMapper = UserMapper.INSTANCE;

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public MessageDTO create(UserDTO userToCreateDTO) {
        verifyAndGetIfExists(userToCreateDTO.getEmail(), userToCreateDTO.getUsername());
        User userToCreate = userMapper.toModel(userToCreateDTO);
        User createdUser = userRepository.save(userToCreate);
        return creationMessage(createdUser, "created");
    }

    public MessageDTO update(Long id, UserDTO userToUpdateDTO) {
        User foundUser = verifyAndGetIfExists(id);

        userToUpdateDTO.setId(foundUser.getId());
        User userToUpdate = userMapper.toModel(userToUpdateDTO);
        userToUpdate.setCreatedDate(foundUser.getCreatedDate());

        User updatedUser = userRepository.save(userToUpdate);
        return updatedMessage(updatedUser, "updated");
    }

    public void delete(Long id) {
        verifyAndGetIfExists(id);
        userRepository.deleteById(id);
    }

    private User verifyAndGetIfExists(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    private void verifyAndGetIfExists(String email, String username) {
        Optional<User> foundUser = userRepository.findByEmailOrUsername(email, username);
        if (foundUser.isPresent()) {
            throw new UserAlreadyExistsException(email, username);
        }
    }




}
