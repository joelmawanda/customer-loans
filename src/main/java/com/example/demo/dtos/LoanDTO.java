package com.example.demo.dtos;

import com.example.demo.entities.Loan;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LoanDTO {

    private Long customerId;

    private Long loanId;

    private LocalDateTime disbursementDate;

    private BigDecimal outstandingAmount;

    public LoanDTO(Loan loan) {
        this.customerId = loan.getCustomer().getCustomerId();
        this.loanId = loan.getLoanId();
        this.disbursementDate = loan.getDisbursementDate();
        this.outstandingAmount = loan.getOutstandingAmount();
    }
}
