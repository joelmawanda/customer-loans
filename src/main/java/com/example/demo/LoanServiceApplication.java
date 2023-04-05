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
import org.springframework.web.client.RestTemplate;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Optional;
import java.util.Random;

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
		int numLoans = random.nextInt(5) + 1; // Generate a random number of loans between 1 and 5 (inclusive)
		for (int i = 0; i < numCustomers; i++) {
			int accountNumber = random.nextInt(900000000) + 1000000000; // Generate a random 10-digit number
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
				int randomAmount = random.nextInt(100001); // Generates a random integer between 0 and 100000 (inclusive)
				BigDecimal randomBigDecimal = BigDecimal.valueOf(randomAmount);
				loan.setOutstandingAmount(randomBigDecimal);
				loan.setCustomer(customer);
				loanRepository.save(loan);
			}
		}

		// Define the endpoint URL
		int accountNumber = 1107809807;
		String endpointUrl = "http://localhost:8080/api/v1/loan-status?accountNumber=" + accountNumber;

		// Define the headers (if any)
		HttpHeaders headers = new HttpHeaders();
		String auth = "admin:admin";
		byte[] encodedAuth = Base64.getEncoder().encode(auth.getBytes(StandardCharsets.US_ASCII));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.set("Authorization", authHeader);

		// Create a RestTemplate object
		RestTemplate restTemplate = new RestTemplate();

		// Make the API call
		ResponseEntity<String> response = restTemplate.exchange(endpointUrl, HttpMethod.GET, new HttpEntity<>(headers), String.class);

		// Check the response status code
		if (response.getStatusCode() == HttpStatus.OK) {
			// Print the response content
			System.out.println(response.getBody());
		} else {
			// Handle the error
			System.out.println("API call failed with status code: " + response.getStatusCode());
		}
	}
}

