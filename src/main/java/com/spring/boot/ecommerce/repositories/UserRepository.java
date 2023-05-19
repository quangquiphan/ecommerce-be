package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.common.enums.Status;
import com.spring.boot.ecommerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    User getByEmailAndStatus(String email, Status status);
    User getById(String id);

    @Query(value = "SELECT COUNT(*) FROM User u WHERE u.userRole = 1")
    int countById();
}
