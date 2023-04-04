package com.example.demo.controllers;

import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import com.example.demo.dtos.LoanStatus;
import com.example.demo.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @GetMapping("/loan-status")
    public ResponseEntity<?> getLoanStatus(@RequestParam("accountNumber") int accountNumber) {
        LoanStatus loans = loanService.getLoanStatus(accountNumber);
        System.out.println("oooooooooooooooooooooo " + loans);
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, loans), HttpStatus.OK);
    }
}

