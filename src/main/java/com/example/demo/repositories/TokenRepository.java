package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

//    boolean findByUsername(String username);

//    Optional<Token> findByUsername(String username);
    Token findByUsername(String username);
}
