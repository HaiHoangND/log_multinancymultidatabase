package com.viettel.miniproject.multitenancymultidatabase.repository;

import com.viettel.miniproject.multitenancymultidatabase.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
