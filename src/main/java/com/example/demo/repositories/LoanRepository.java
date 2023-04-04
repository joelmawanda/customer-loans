package com.example.demo.repositories;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByCustomer(Customer customer);
}
