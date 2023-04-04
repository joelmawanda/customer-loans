package com.example.demo.services;

import com.example.demo.dtos.LoanStatus;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.exceptions.InvalidAccountNumberException;
import com.example.demo.exceptions.NoLoanFoundException;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanStatus getLoanStatus(int accountNumber) throws InvalidAccountNumberException, NoLoanFoundException {
        Optional<Customer> optionalCustomer = customerRepository.findByAccountNumber(accountNumber);
        if (optionalCustomer.isEmpty()) {
            throw new InvalidAccountNumberException("Invalid account number");
        }
        Customer customer = optionalCustomer.get();
        List<Loan> loans = loanRepository.findByCustomer(customer);
        if (loans.isEmpty()) {
            throw new NoLoanFoundException("No loan found");
        }
        return new LoanStatus(loans);
    }


}

