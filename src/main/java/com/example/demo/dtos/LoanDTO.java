package com.example.demo.dtos;

import com.example.demo.entities.Loan;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({
        "customer_id",
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

}
