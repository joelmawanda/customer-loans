package com.example.demo.dtos;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
public class CustomerDTO {
    private Long customerId;

    private int accountNumber;

    private Set<Loan> loans;

    public CustomerDTO(Customer customer) {
        this.customerId = customer.getCustomerId();
        this.accountNumber = customer.getAccountNumber();
        this.loans = customer.getLoans();

    }
}
