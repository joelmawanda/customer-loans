package com.example.demo.dtos;

import com.example.demo.entities.Loan;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class LoanStatus {

    private List<LoanDTO> loans;

    public LoanStatus(List<Loan> loans) {
        this.loans = loans.stream().map(LoanDTO::new).collect(Collectors.toList());
    }
}
