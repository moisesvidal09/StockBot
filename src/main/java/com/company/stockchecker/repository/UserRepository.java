package com.company.stockchecker.repository;


import com.company.stockchecker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Long, User> {
}
