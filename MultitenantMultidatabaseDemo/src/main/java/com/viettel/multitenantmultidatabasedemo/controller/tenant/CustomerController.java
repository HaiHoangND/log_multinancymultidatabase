package com.viettel.multitenantmultidatabasedemo.controller.tenant;

import com.viettel.multitenantmultidatabasedemo.constant.SpringDataJPAMultitenantConstants;
import com.viettel.multitenantmultidatabasedemo.context.TenantContext;
import com.viettel.multitenantmultidatabasedemo.dtos.JwtResponse;
import com.viettel.multitenantmultidatabasedemo.dtos.LoginRequest;
import com.viettel.multitenantmultidatabasedemo.entity.master.MultitenantcyMasterDatasourceEntity;
import com.viettel.multitenantmultidatabasedemo.entity.tenant.CustomerEntity;
import com.viettel.multitenantmultidatabasedemo.jpa.TenantDataSource;
import com.viettel.multitenantmultidatabasedemo.repository.master.IMultitenancyMasterDatasourceRepository;
import com.viettel.multitenantmultidatabasedemo.security.jwt.JwtUtils;
import com.viettel.multitenantmultidatabasedemo.security.services.UserDetailsImpl;
import com.viettel.multitenantmultidatabasedemo.service.master.IMultitenancyMasterDatasourceService;
import com.viettel.multitenantmultidatabasedemo.service.tenant.ICustomerService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Log4j2
public class CustomerController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private ICustomerService customerService;


    @GetMapping("/customers")
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestHeader("X-TenantID") String tenantId, @Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication, tenantId);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getFullName(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getAddress()));
    }

    @PutMapping(value = SpringDataJPAMultitenantConstants.EDIT_CUSTOMER_PATH)
    public ResponseEntity<String> editCustomer(@PathVariable Long id, @RequestBody CustomerEntity customer) {
        log.debug("Editing customer information {}", customer.getId());
        customerService.update(id, customer);
        return ResponseEntity.ok("Cập nhật người dùng có id là " + id + " thành công");
    }

    @PostMapping(value = SpringDataJPAMultitenantConstants.ADD_CUSTOMER_PATH)
    @Transactional
    public ResponseEntity<String> addCustomer(@RequestBody CustomerEntity customer) {
        customer.setPassword(encoder.encode(customer.getPassword()));
        customerService.save(customer);
        return ResponseEntity.ok("Tạo người dùng thành công");
    }

    @DeleteMapping(value = SpringDataJPAMultitenantConstants.DELETE_CUSTOMER_PATH)
    @Transactional
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.ok("Xóa người dùng thành công");
    }
}
