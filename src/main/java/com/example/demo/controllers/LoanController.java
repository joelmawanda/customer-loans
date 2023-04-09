package com.example.demo.controllers;

import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import com.example.demo.dtos.AuthRequest;
import com.example.demo.dtos.LoanStatus;
import com.example.demo.services.JwtService;
import com.example.demo.services.LoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/status")
    public ResponseEntity<?> getLoanStatus(@RequestParam("accountNumber") int accountNumber, @RequestHeader String Authorization) {
        LoanStatus loans = loanService.getLoanStatus(accountNumber);
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "These are the customer's loans", loans), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            String token = jwtService.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "Authenticated Successfully", token), HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, "Failed to Authenticate user"), HttpStatus.BAD_REQUEST);
        }

    }
}

