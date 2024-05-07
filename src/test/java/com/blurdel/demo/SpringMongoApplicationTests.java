package com.blurdel.demo;

import com.blurdel.demo.model.Customer;
import com.blurdel.demo.repositories.CustomerRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SpringMongoApplicationTests {

	@Autowired
	private CustomerRepository repository;

	@Test
	void contextLoads() {
		assertNotNull(repository);
	}

	@Test
	@Order(1)
	void initDatabase() {
		if (repository.count() == 0) {
			repository.save(new Customer("Alice", "Smith"));
			repository.save(new Customer("Bob", "Smith"));
		}
	}

	@Test
	void testGetAll() {
		List<Customer> list = new ArrayList();
		repository.findAll().forEach(list::add);
		assertTrue(list.size() == 2, "Should be 2 Customers");

//		for (Customer c : repository.findAll()) {
//			System.out.println(c.toString());
//		}
	}

	@Test
	void testGetByFirstName() {
		Customer alice = repository.findByFirstName("Alice");
		assertNotNull(alice, "Should be 1 Alice");
		assertTrue("Alice".equals(alice.getFirstName()));
	}

	@Test
	void testGetByLastName() {
		List<Customer> list = repository.findByLastName("Smith");
		assertTrue(list.size() == 2, "Should be 2 Smiths");
	}

}
