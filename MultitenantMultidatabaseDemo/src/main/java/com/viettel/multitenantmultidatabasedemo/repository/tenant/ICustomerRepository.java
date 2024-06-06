package com.viettel.multitenantmultidatabasedemo.repository.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ICustomerRepository extends JpaRepository<CustomerEntity, Long> {
    Optional<CustomerEntity> findByUsername(String username);
}
