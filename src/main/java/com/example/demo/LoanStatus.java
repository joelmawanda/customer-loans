package com.example.demo;

import com.example.demo.entities.Loan;
import lombok.Data;
import java.util.List;
import java.util.stream.Collectors;

@Deprecated
@Data
public class LoanStatus {

    private List<LoanDetails> loans;

    public LoanStatus(List<Loan> loans) {
        this.loans = loans.stream().map(LoanDetails::new).collect(Collectors.toList());
    }


}
