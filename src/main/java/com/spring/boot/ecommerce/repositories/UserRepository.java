package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getByEmailAndStatus(String email, Status status);
    User getById(String id);
}
