package com.redoute.demo.service;

import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.redoute.demo.entity.Customer;
import com.redoute.demo.repository.CustomerRepository;
import com.redoute.demo.service.exception.CustomerNotFoundException;

/**
 * A service use to implement all the business rules. Business rules should not be on view, it's service who send to view business errors
 *  
 * @author ndeblock
 *
 */
@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerRepository customerRepository;
	
	@Autowired
    private Validator validator;
	
	/**
	 * Save customer on database. Verify if business rules on customer before. 
	 * @param customer
	 * @return
	 * @throws ConstraintViolationException when customer don't respect business rules. Exception contain the error.
	 */
	public Customer saveCustomer(Customer customer) throws ConstraintViolationException {
		// first, verify integrity of object
		Set<ConstraintViolation<Customer>> violations = validator.validate(customer);

		// if no error, we can save it
		if(violations.isEmpty()) {
			return customerRepository.save(customer);
		}
		else {
			// else, throw the error
			throw new ConstraintViolationException(violations);
		}
	}
	
	/**
	 * Find customer with the id parameter 
	 * @param id
	 * @return
	 * @throws CustomerNotFoundException when no customer found for the id
	 */
	public Customer findCustomer(Long id) throws CustomerNotFoundException {
		Customer cust = customerRepository.findOne(id);
		
		if(cust == null) {
			throw new CustomerNotFoundException();
		}
		
		return cust;
	}
}
