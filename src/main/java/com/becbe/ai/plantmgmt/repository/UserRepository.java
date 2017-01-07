package com.archius.cosmos.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.archius.cosmos.model.User;
/**
 * @author Thulasiram
 *
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
