package com.spring.boot.ecommerce.repositories;

import com.spring.boot.ecommerce.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, String> {
}
