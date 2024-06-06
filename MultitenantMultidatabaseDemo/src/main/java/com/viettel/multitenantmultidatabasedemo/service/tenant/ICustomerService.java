package com.viettel.multitenantmultidatabasedemo.service.tenant;

import com.viettel.multitenantmultidatabasedemo.entity.tenant.CustomerEntity;

import java.util.List;

public interface ICustomerService {
    public List<CustomerEntity> findAll();

    public CustomerEntity findById(long Id);

    public void save(CustomerEntity customer);

    public void update(Long id, CustomerEntity customer);

    public void delete(long Id);
}
