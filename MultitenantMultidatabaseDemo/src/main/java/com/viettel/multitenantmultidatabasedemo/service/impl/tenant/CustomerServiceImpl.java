package com.viettel.multitenantmultidatabasedemo.service.impl.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.CustomerEntity;
import com.viettel.multitenantmultidatabasedemo.exception.CustomerNotFoundException;
import com.viettel.multitenantmultidatabasedemo.repository.tenant.ICustomerRepository;
import com.viettel.multitenantmultidatabasedemo.service.tenant.ICustomerService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private ICustomerRepository customerRepository;

    @Autowired
    PasswordEncoder encoder;

    @Override
    public List<CustomerEntity> findAll() {
        log.info("INFO Message: Fetching all users");
        List<CustomerEntity> users = customerRepository.findAll();
        log.info("INFO Message: Total users found: {}", users.size());
        return users;
    }

    @Override
    public void save(CustomerEntity customer) {
        log.trace("A test TRACE Message at " + DateTimeFormatter.ofPattern("HH:mm:ss").format(LocalDateTime.now()));
        log.info("INFO Message: Creating user: {}", customer);
        CustomerEntity savedUser = customerRepository.save(customer);
        log.info("INFO Message: User created successfully: {}", savedUser);
    }

    @Override
    public void update(Long id, CustomerEntity customer) {
        log.info("INFO Message: Updating user with ID: {}", id);
        Optional<CustomerEntity> existingUser = customerRepository.findById(id);
        if (existingUser.isPresent()) {
            CustomerEntity customerEntity = existingUser.get();
            log.debug("DEBUG Message: Current user data: {}", customerEntity);
            customerEntity.setFullName(customer.getFullName());
            customerEntity.setAddress(customer.getAddress());
            customerEntity.setEmail(customer.getEmail());
            customerEntity.setPassword(encoder.encode(customer.getPassword()));
            customerEntity.setUsername(customer.getUsername());
            CustomerEntity updatedUser = customerRepository.save(customerEntity);
            log.info("INFO Message: User updated successfully: {}", updatedUser);
        } else {
            log.error("ERROR Message: User with ID {} not found for update", id);
            throw new CustomerNotFoundException("Customer { " + id + "} doest not exits");
        }
    }

    @Override
    public CustomerEntity findById(long id) {
        Optional<CustomerEntity> optionalUser = customerRepository.findById(id);
        if (optionalUser.isPresent()) {
            CustomerEntity user = optionalUser.get();
            log.info("INFO Message: User found: {}", user);
            return user;
        } else {
            log.error("ERROR Message: User with ID {} not found", id);
            throw new CustomerNotFoundException("Customer { " + id + "} doest not exits");
        }
    }

    @Override
    public void delete(long id) {
        log.info("INFO Message: Deleting user with ID: {}", id);
        try {
            customerRepository.deleteById(id);
            log.info("INFO Message: User with ID {} deleted successfully", id);
        } catch (Exception e) {
            log.error("ERROR Message: Error deleting user with ID {}", id, e);
            throw e;
        }
    }

}
