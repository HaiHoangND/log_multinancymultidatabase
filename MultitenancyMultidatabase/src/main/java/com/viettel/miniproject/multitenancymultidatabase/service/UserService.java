package com.viettel.miniproject.multitenancymultidatabase.service;

import com.viettel.miniproject.multitenancymultidatabase.entity.User;
import com.viettel.miniproject.multitenancymultidatabase.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        log.trace("A test TRACE Message at " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        log.info("INFO Message: Creating user: {}", user);
        User savedUser = userRepository.save(user);
        log.info("INFO Message: User created successfully: {}", savedUser);
        return savedUser;
    }

    public User getUserById(Long userId) {
        log.info("INFO Message: Fetching user with ID: {}", userId);
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            log.info("INFO Message: User found: {}", user);
            return user;
        } else {
            log.error("ERROR Message: User with ID {} not found", userId);
            throw new RuntimeException("Không tìm thấy người dùng");
        }
    }

    public List<User> getAllUsers() {
        log.info("INFO Message: Fetching all users");
        List<User> users = userRepository.findAll();
        log.info("INFO Message: Total users found: {}", users.size());
        return users;
    }

    public User updateUser(Long id, User user) {
        log.info("INFO Message: Updating user with ID: {}", id);
        Optional<User> existingUser = userRepository.findById(id);
        if (existingUser.isPresent()) {
            User oldUser = existingUser.get();
            log.debug("DEBUG Message: Current user data: {}", oldUser);
            oldUser.setName(user.getName());
            oldUser.setAge(user.getAge());
            User updatedUser = userRepository.save(oldUser);
            log.info("INFO Message: User updated successfully: {}", updatedUser);
            return updatedUser;
        } else {
            log.error("ERROR Message: User with ID {} not found for update", id);
            throw new RuntimeException("Không tồn tại người dùng cần cập nhật");
        }
    }

    public void deleteUser(Long userId) {
        log.info("INFO Message: Deleting user with ID: {}", userId);
        try {
            userRepository.deleteById(userId);
            log.info("INFO Message: User with ID {} deleted successfully", userId);
        } catch (Exception e) {
            log.error("ERROR Message: Error deleting user with ID {}", userId, e);
            throw e;
        }
    }
}
