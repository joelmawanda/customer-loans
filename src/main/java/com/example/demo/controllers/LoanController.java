package com.example.demo.controllers;

import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import com.example.demo.dtos.AuthRequest;
import com.example.demo.dtos.CustomerDTO;
import com.example.demo.dtos.LoanDTO;
import com.example.demo.services.JwtService;
import com.example.demo.services.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
@ApiResponses(value = {

        @ApiResponse(responseCode = "200", description="Successful operation"),
        @ApiResponse(responseCode = "400", description="Bad Request. The request was invalid or cannot be served"),
        @ApiResponse(responseCode = "401", description="Unauthorized. The authentication failed"),
        @ApiResponse(responseCode = "403", description="Forbidden. The user may not be having the necessary permissions for a resource"),
        @ApiResponse(responseCode = "404", description="Not Found. The resource could not be found"),
        @ApiResponse(responseCode = "500", description="Internal server error. The server encountered an unexpected condition which prevented it fulfilling the request"),
        @ApiResponse(responseCode = "timestamp", description="The time at which the api was invoked"),
        @ApiResponse(responseCode = "operation_result", description="0 means Success while 1 means Failed"),
        @ApiResponse(responseCode = "operation_description", description="Success describes operation_result of 0 whereas Failed describes operation_result of 1"),
        @ApiResponse(responseCode = "message", description="The response message for the api operation")

})
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Operation(summary = "Get a customer's loans by their account number", description = "Returns a list of all the customers loans")
    @GetMapping("/status")
    public ResponseEntity<?> getLoanStatus(@RequestParam("accountNumber") int accountNumber, @RequestHeader String Authorization) {
        List<LoanDTO> loans = loanService.getLoanStatus(accountNumber);
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "These are the customer's loans", loans), HttpStatus.OK);
    }

    @Operation(summary = "Get all customers", description = "Returns a list of all the customers")
    @PostMapping("/customers")
    public ResponseEntity<?> getCustomers(@RequestHeader String Authorization) {
        List<CustomerDTO> customers = loanService.getCustomers();
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "These are the customers", customers), HttpStatus.OK);
    }

    @Operation(summary = "Authenticate user", description = "Authenticates a user and generates a JWT token for the user")
    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            String token = jwtService.generateToken(authRequest.getUsername());
            return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_SUCCESS_CODE, Constants.OPERATION_SUCCESS_DESCRIPTION, "Authenticated Successfully", token), HttpStatus.OK);
    }
}

