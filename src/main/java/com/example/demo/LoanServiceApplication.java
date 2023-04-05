//package com.example.demo;
//
//import com.example.demo.entities.Customer;
//import com.example.demo.entities.Loan;
//import com.example.demo.exceptions.InvalidAccountNumberException;
//import com.example.demo.repositories.CustomerRepository;
//import com.example.demo.repositories.LoanRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//
//import java.math.BigDecimal;
//import java.math.RoundingMode;
//import java.util.Optional;
//import java.util.Random;
//
//@SpringBootApplication
//public class LoanServiceApplication implements CommandLineRunner {
//
//	public static void main(String[] args) {
//		SpringApplication.run(LoanServiceApplication.class, args);
//	}
//
//	@Autowired
//	CustomerRepository customerRepository;
//
//	@Autowired
//	LoanRepository loanRepository;
//
//	@Override
//	public void run(String... args) throws Exception {
//
//		Random random = new Random();
//
//		int numCustomers = 10;
//		int numLoans = random.nextInt(5) + 1; // Generate a random number of loans between 1 and 5 (inclusive)
//		for (int i = 0; i < numCustomers; i++) {
//			int accountNumber = random.nextInt(900000000) + 1000000000; // Generate a random 10-digit number
//			Optional<Customer> optionalCustomer = customerRepository.findByAccountNumber(accountNumber);
//
//			Customer customer;
//			if (optionalCustomer.isPresent()) {
//				customer = optionalCustomer.get();
//			} else {
//				customer = new Customer();
//				customer.setAccountNumber(accountNumber);
//				customerRepository.save(customer);
//			}
//
//			for (int j = 0; j < numLoans; j++) {
//				Loan loan = new Loan();
//				int randomAmount = random.nextInt(100001); // Generates a random integer between 0 and 100000 (inclusive)
//				BigDecimal randomBigDecimal = BigDecimal.valueOf(randomAmount);
//				loan.setOutstandingAmount(randomBigDecimal);
//				loan.setCustomer(customer);
//				loanRepository.save(loan);
//			}
//		}
//	}
//}


package com.example.demo;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.*;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class LoanServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoanRepository loanRepository;

	@Override
	public void run(String... args) throws Exception {

		Random random = new Random();

		int numCustomers = 10;
		int numLoans = random.nextInt(5) + 1;
		for (int i = 0; i < numCustomers; i++) {
			int accountNumber = random.nextInt(900000000) + 1000000000;
			Optional<Customer> optionalCustomer = customerRepository.findByAccountNumber(accountNumber);

			Customer customer;
			if (optionalCustomer.isPresent()) {
				customer = optionalCustomer.get();
			} else {
				customer = new Customer();
				customer.setAccountNumber(accountNumber);
				customerRepository.save(customer);
			}

			for (int j = 0; j < numLoans; j++) {
				Loan loan = new Loan();
				int randomAmount = random.nextInt(100001);
				BigDecimal randomBigDecimal = BigDecimal.valueOf(randomAmount);
				loan.setOutstandingAmount(randomBigDecimal);
				loan.setCustomer(customer);
				loanRepository.save(loan);
			}
		}

		String endpointUrl = "http://localhost:8080/api/v1/loan-status";

		HttpHeaders headers = new HttpHeaders();
		String auth = "admin:admin";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);

		RestTemplate restTemplate = new RestTemplate();
		
		List<Integer> accountNumbers = new ArrayList<>();

		for (int i = 0; i < 3; i++) {
			int accountNumber = 1000000000 + random.nextInt(900000000);
			accountNumbers.add(accountNumber);
		}

		for (int accountNumber : accountNumbers) {
			String url = endpointUrl + "?accountNumber=" + accountNumber;

			ResponseEntity<String> response;
			try {
				response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
				if (response.getStatusCode() == HttpStatus.OK) {
					try {
						FileWriter writer = new FileWriter("response-" + accountNumber + ".txt");
						writer.write(response.getBody());
						writer.close();
					} catch (IOException e) {
						System.out.println("Error writing response to file: " + e.getMessage());
					}
				} else {
					System.out.println("API call failed with status code: " + response.getStatusCode());
				}
			} catch (HttpStatusCodeException ex) {
				if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
					try {
						FileWriter writer = new FileWriter("response-" + accountNumber + ".txt");
						writer.write(ex.getResponseBodyAsString());
						writer.close();
					} catch (IOException e) {
						System.out.println("Error writing response to file: " + e.getMessage());
					}
				} else {
					System.out.println("API call failed with error: " + ex.getLocalizedMessage());
				}
			}  catch (Exception ex) {
				System.out.println("An error occurred: " + ex.getMessage());
			}
		}
	}
}

