package crm.controller;

import crm.model.Customer;
import crm.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	private CustomerRepository customers;

	@GetMapping("/{id}")
	public Customer getCustomer(@PathVariable Long id) {
		validateCustomer(id);
		Customer e = customers.findOne(id);
		return e;
	}

	@GetMapping("")
	public List<Customer> getAllCustomers() {
		List<Customer> customers = new ArrayList<>();
		this.customers.findAll().forEach(customers::add);
		return customers;
	}

	@PostMapping
	public ResponseEntity<?> addCustomer(@RequestBody Customer c) {
		if(c != null) {
			Long id = customers.save(c).getId();
			return ResponseEntity.created(ServletUriComponentsBuilder
			.fromCurrentRequestUri().path("/id")
			.buildAndExpand(id).toUri()).build();
		}
		return ResponseEntity.noContent().build();
	}

	private void validateCustomer(Long id) {
		if (this.customers.findOne(id) == null) {throw new CustomerNotFoundException(id);}
	}

}
