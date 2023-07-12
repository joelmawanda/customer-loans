package com.example.demo.services;

import com.example.demo.dtos.CustomerDTO;
import com.example.demo.dtos.LoanDTO;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.exceptions.InvalidAccountNumberException;
import com.example.demo.exceptions.NoLoanFoundException;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
public class LoanService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    private final ModelMapper modelMapper;

    public LoanService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public List<LoanDTO> getLoanStatus(int accountNumber) throws InvalidAccountNumberException, NoLoanFoundException {
        log.info("[Inside the getLoanStatus method]: Retrieving customer loans for account number " + accountNumber + " from the database");
        Optional<Customer> optionalCustomer = customerRepository.findByAccountNumber(accountNumber);
        if (optionalCustomer.isEmpty()) {
            throw new InvalidAccountNumberException("Invalid account number");
        }
        Customer customer = optionalCustomer.get();
        List<Loan> loans = loanRepository.findByCustomer(customer);
        if (loans.isEmpty()) {
            throw new NoLoanFoundException("No loan found");
        }
        return  loans.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private LoanDTO convertToDto(Loan loan) {
        LoanDTO loanDTO = modelMapper.map(loan, LoanDTO.class);
        loanDTO.setCustomerId(loan.getCustomer().getCustomerId());
        loanDTO.setLoanId(loan.getLoanId());
        loanDTO.setDisbursementDate(loan.getDisbursementDate());
        loanDTO.setOutstandingAmount(loan.getOutstandingAmount());
        return loanDTO;
    }

    private CustomerDTO convertToCustomerDto(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer, CustomerDTO.class);
        customerDTO.setCustomerId(customer.getCustomerId());
        customerDTO.setAccountNumber(customer.getAccountNumber());
        customerDTO.setLoans(customer.getLoans());
        return customerDTO;
    }

    public List<CustomerDTO> getCustomers (CustomerDTO customerDTO) {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(this::convertToCustomerDto).collect(Collectors.toList());
    }
}

