package com.viettel.multitenantmultidatabasedemo.security.services;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.CustomerEntity;
import com.viettel.multitenantmultidatabasedemo.repository.tenant.ICustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    ICustomerRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomerEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Người dùng không tìm thấy với tên: " + username));

        return UserDetailsImpl.build(user);
    }
}
