package com.example.demo.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NamedEntityGraph(name = "Customer.withLoans",
        attributeNodes = {
                @NamedAttributeNode(value = "loans")
        }
)

@Entity
@Table(name = "customers")
@Data
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_number", nullable = false, unique = true)
    private int accountNumber;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
//    private Set<Loan> loans;
    private Set<Loan> loans = new HashSet<>();


}

