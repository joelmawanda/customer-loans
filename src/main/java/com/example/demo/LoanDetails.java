package com.example.demo;

import com.example.demo.entities.Loan;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Deprecated
@Data
public class LoanDetails {

    private Long customerId;

    private Long loanId;

    private LocalDateTime disbursementDate;

    private BigDecimal outstandingAmount;

    public LoanDetails(Loan loan) {
        this.customerId = loan.getId();
        this.loanId = loan.getId();
        this.disbursementDate = loan.getDisbursementDate();
        this.outstandingAmount = loan.getOutstandingAmount();
    }
}
