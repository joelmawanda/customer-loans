package com.example.demo.services;

import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import com.example.demo.dtos.LoanDTO;
import com.example.demo.dtos.LoanStatus;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.exceptions.InvalidAccountNumberException;
import com.example.demo.exceptions.NoLoanFoundException;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class LoanService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanRepository loanRepository;

    public LoanStatus getLoanStatus(int accountNumber) throws InvalidAccountNumberException, NoLoanFoundException {
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
        return new LoanStatus(loans);
    }

    public OperationResponse findAll(Pageable pageable, String direction) {
        log.info(">>> " + pageable);
        log.info(">>> sorting by direction: " + direction);

        Page<Customer> page = customerRepository.findAll(
                PageRequest.of(pageable.getPageNumber(),
                        pageable.getPageSize()
                ));

        log.info("system found " + page.getTotalElements() + " element(s)");

        OperationResponse operationResponse;
        List<Customer> list = page.getContent();

        if (page.getContent().size() <= 0) {
            operationResponse = new OperationResponse(
                    Constants.OPERATION_SUCCESS_CODE,
                    Constants.OPERATION_SUCCESS_DESCRIPTION,
                    "No Records Found",
                    list, 0, 0, 0);
        } else {
            operationResponse = new OperationResponse(
                    Constants.OPERATION_SUCCESS_CODE,
                    Constants.OPERATION_SUCCESS_DESCRIPTION,
                    page.getTotalElements() + " Record(s) found.",
                    list,
                    page.getNumber(),
                    page.getNumberOfElements(),
                    page.getTotalPages());
        }
        return operationResponse;
    }
}

