
package com.example.demo;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.entities.UserInfo;
import com.example.demo.exceptions.GenericServiceException;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import com.example.demo.repositories.UserInfoRepository;
import com.example.demo.services.JwtService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
@OpenAPIDefinition(info = @Info(title = "Customer Products API", version = "1.0", description = "This API returns a customer's loans by account number"))
@Slf4j
public class LoanServiceApplication implements CommandLineRunner {

	private static  final String DEFAULT_ADMIN_USERNAME = "admin";
	private static  final String DEFAULT_ADMIN_PASSWORD = "admin";

	public static void main(String[] args) {
		SpringApplication.run(LoanServiceApplication.class, args);
	}

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoanRepository loanRepository;

	@Autowired
	UserInfoRepository userInfoRepository;

	@Autowired
	JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public void run(String... args) throws Exception {

		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		String adminUsername = DEFAULT_ADMIN_USERNAME;

		log.info("Searching for user with name: " + adminUsername);

		Optional<UserInfo> optionalUser = userInfoRepository.findByName(adminUsername);
		if (optionalUser.isPresent()) {
			log.info("Found User: " + optionalUser.get());
		} else {
			log.info("User not found, creating new user with default values");
			UserInfo newUser = new UserInfo();
			newUser.setName(DEFAULT_ADMIN_USERNAME);
			newUser.setPassword(passwordEncoder.encode(DEFAULT_ADMIN_PASSWORD));
			userInfoRepository.save(newUser);
			log.info("New user created: " + newUser);
		}

		log.info("[Inside the CommandLineRunner method]: persisting customer data to the database");

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

		String token = null;

		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(DEFAULT_ADMIN_USERNAME, DEFAULT_ADMIN_PASSWORD));
		if (authentication.isAuthenticated()) {
			token = jwtService.generateToken(DEFAULT_ADMIN_USERNAME);
			log.info("The generated token is: " + token);
		} else {
			throw new UsernameNotFoundException("invalid user request !");
		}

		log.info("[Inside the CommandLineRunner method]: calling the RestTemplate class which invokes the api");

		String endpointUrl = "http://localhost:8080/api/v1/loans/status";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + token);

		RestTemplate restTemplate = new RestTemplate();

		List<Integer> accountNumbers = new ArrayList<>();

		int numAccounts = 5;

		for (int i = 0; i < numAccounts; i++) {
			int accountNumber = 1000000000 + random.nextInt(900000000);
			accountNumbers.add(accountNumber);
		}
		try {
			FileWriter writer = new FileWriter("response.txt", true);
			for (int accountNumber : accountNumbers) {
				String url = endpointUrl + "?accountNumber=" + accountNumber;

				ResponseEntity<String> response;

				try {
					response = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

					if (response.getStatusCode() == HttpStatus.OK) {
						writer.write(response.getBody());
						writer.write("\n");
					} else {
						log.info("API call failed with status code: " + response.getStatusCode());
					}
				} catch (HttpStatusCodeException ex) {
					if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
						writer.write(ex.getResponseBodyAsString());
						writer.write("\n");
					} else {
						log.info("API call failed with error: " + ex.getLocalizedMessage());
					}
				} catch (Exception ex) {
					throw new GenericServiceException("An error occurred: " + ex.getLocalizedMessage());
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new IOException("Error writing response to file: " + e.getMessage());
		}


	}
}

