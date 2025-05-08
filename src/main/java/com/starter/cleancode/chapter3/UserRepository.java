package com.starter.cleancode.chapter3;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {
    
    @Query("select u from User u where u.email = email and u.id <> userId")
    boolean existsByEmailExceptForUserId(String email, Long userId);

    boolean existsByEmail(String email);
}
