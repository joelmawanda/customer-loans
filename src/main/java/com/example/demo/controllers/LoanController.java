package com.example.demo.controllers;

import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import com.example.demo.dtos.AuthRequest;
import com.example.demo.dtos.LoanStatus;
import com.example.demo.entities.Loan;
import com.example.demo.services.JwtService;
import com.example.demo.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @GetMapping("/loans/status")
    public ResponseEntity<?> getLoanStatus(@RequestParam("accountNumber") int accountNumber) {
        LoanStatus loans = loanService.getLoanStatus(accountNumber);
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "These are the customer's loans", loans), HttpStatus.OK);
    }

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/customers")
    public ResponseEntity<?> findAll(Pageable page, @RequestParam(name = "direction", defaultValue = "asc") String direction) {
        return new ResponseEntity<>(loanService.findAll(page, direction), HttpStatus.OK);
    }
}

