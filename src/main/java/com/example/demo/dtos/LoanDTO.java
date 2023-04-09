package com.example.demo.dtos;

import com.example.demo.entities.Loan;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({
        "customer_id",
        "customer_name",
        "account_number",
        "loan_id",
        "disbursement_date",
        "outstanding_Amount"
})
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
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
