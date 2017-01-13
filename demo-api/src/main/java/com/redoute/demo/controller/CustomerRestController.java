package com.redoute.demo.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.redoute.demo.dto.CustomerDTO;
import com.redoute.demo.dto.converter.CustomerDTOConverter;
import com.redoute.demo.entity.Customer;
import com.redoute.demo.service.CustomerService;
import com.redoute.demo.service.exception.CustomerNotFoundException;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerService customerService;
	
	@Autowired
	private CustomerDTOConverter customerConverter;
	
	 @RequestMapping("/customer") 
	 public CustomerDTO getCustomer(@RequestParam(value="id") Long id, HttpServletResponse response) {
		 Customer c=null;

		 try {
			c = customerService.findCustomer(id);
		} catch (CustomerNotFoundException e) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
			return new CustomerDTO(); // return an empty customer
		}

		 return customerConverter.customerToCustomerDTO(c);
	 }
}
