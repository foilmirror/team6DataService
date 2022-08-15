package com.bah.msd.mcc.api;

import java.net.URI;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.bah.msd.mcc.domain.Customer;
import com.bah.msd.mcc.repository.CustomersRepository;

@RestController
@RequestMapping("/customers")
public class CustomersAPI {
	@Autowired
	CustomersRepository repo;

	@GetMapping
	public Iterable<Customer> getAll() {
		return repo.findAll(); 
	}

	@GetMapping("/{customerId}")
	public Optional<Customer> getCustomerById(@PathVariable("customerId") long id) {
		return repo.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody Customer newCustomer, UriComponentsBuilder uri) {
		if (newCustomer.getId()!=0 || newCustomer.getName()==null || newCustomer.getEmail() == null) { 
			return ResponseEntity.badRequest().build();
		}
		newCustomer=repo.save(newCustomer);
		URI location=ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newCustomer.getId()).toUri();
		ResponseEntity<?> response=ResponseEntity.created(location).build();
		return response;
	}

	//lookupCustomerByName GET
	@GetMapping("/byname/{username}")
	public ResponseEntity<?> lookupCustomerByNameGet(@PathVariable("username") String username,
			UriComponentsBuilder uri) {

		Iterator<Customer> customers = repo.findAll().iterator();
		while(customers.hasNext()) {
			Customer currentCust = customers.next();
			if(currentCust.getName().equalsIgnoreCase(username)) {
				ResponseEntity<?> response = ResponseEntity.ok(currentCust);
				return response;
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}
	
	//lookupCustomerByName POST
	@PostMapping("/byname")
	public ResponseEntity<?> lookupCustomerByNamePost(@RequestBody String username, UriComponentsBuilder uri) { 
		
		Iterator<Customer> customers = repo.findAll().iterator();
		while(customers.hasNext()) {
			Customer currentCust = customers.next();
			if(currentCust.getName().equalsIgnoreCase(username)) {
				ResponseEntity<?> response = ResponseEntity.ok(currentCust);
				return response;
			}
		}
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
	}	
	
	
	@PutMapping("/{customerId}")
	public ResponseEntity<?> putCustomer(
			@RequestBody Customer newCustomer,
			@PathVariable("customerId") long customerId) 
	{
		if (newCustomer.getId()!=customerId || newCustomer.getName()==null || newCustomer.getEmail() == null) {
			return ResponseEntity.badRequest().build();
		}
		newCustomer=repo.save(newCustomer);
		return ResponseEntity.ok().build();
	}	
	
	@DeleteMapping("/{customerId}")
	public ResponseEntity<?> deleteCustomerById(@PathVariable("customerId") long id) {
		//  Implement a method to delete a customer.  What is an appropriate response? 
		//
		//  For discussion (do not worry about implementation):  What are some ways of handling 
		//  a "delete"?  Is it always the right thing from a business point of view to literally 
		//  delete a customer entry?  If you did actually delete a customer entry, are there issues
		//  you could potentially run into later? 
		if (repo.findById(id) != null) {
			repo.deleteById(id);
			return ResponseEntity.ok().build();
		}
		else {
			return ResponseEntity.badRequest().build();
		}
	}	
	
	
}