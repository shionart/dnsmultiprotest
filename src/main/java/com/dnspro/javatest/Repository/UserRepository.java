package com.dnspro.javatest.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dnspro.javatest.User.User;

public interface UserRepository extends JpaRepository<User, Integer>{   
    Optional<User> findByUsername(String username);
    Boolean existsByUsername(String username);
}
