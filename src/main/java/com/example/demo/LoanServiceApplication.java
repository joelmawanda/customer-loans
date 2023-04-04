package com.example.demo;

import com.example.demo.entities.Customer;
import com.example.demo.entities.Loan;
import com.example.demo.repositories.CustomerRepository;
import com.example.demo.repositories.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

@SpringBootApplication
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
			Customer customer = new Customer();
			int accountNumber = random.nextInt(900000000) + 1000000000; // Generate a random 10-digit number
			customer.setAccountNumber(accountNumber);
			customerRepository.save(customer);

			for (int j = 0; j < numLoans; j++) {
				Loan loan = new Loan();
				int randomAmount = random.nextInt(100001); // Generates a random integer between 0 and 100000 (inclusive)
				BigDecimal randomBigDecimal = BigDecimal.valueOf(randomAmount);
				loan.setOutstandingAmount(randomBigDecimal);
				loan.setCustomer(customer);
				loanRepository.save(loan);
			}
		}

	}
}
