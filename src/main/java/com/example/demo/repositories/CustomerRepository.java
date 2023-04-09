package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByAccountNumber(int accountNumber);

    @Override
    @EntityGraph(value = "Customer.withLoans", type = EntityGraph.EntityGraphType.FETCH)
    public Page<Customer> findAll(Pageable pageable);
}
