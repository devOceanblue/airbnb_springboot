package com.example.airbnb_clone.repository;

import com.example.airbnb_clone.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
