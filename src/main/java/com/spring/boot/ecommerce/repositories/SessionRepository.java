package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SessionRepository extends JpaRepository<Session, String> {
    Session getById(String token);

    List<Session> findAllByUserId(String id);
}
