package com.redoute.demo.dto.converter;

import org.springframework.stereotype.Component;

import com.redoute.demo.dto.CustomerDTO;
import com.redoute.demo.entity.Customer;

@Component
public class CustomerDTOConverter {

	public CustomerDTO customerToCustomerDTO(Customer c) {
		CustomerDTO res = new CustomerDTO();
				
		res.setId(c.getId());
		res.setFirstName(c.getFirstName());
		res.setLastName(res.getLastName());
		res.setBirthDate(c.getBirthDate());
		
		return res;
	}
	
	public Customer customerToCustomerDTO(CustomerDTO c) {
		Customer res = new Customer();
				
		res.setId(c.getId());
		res.setFirstName(c.getFirstName());
		res.setLastName(res.getLastName());
		res.setBirthDate(c.getBirthDate());
		
		return res;
	}
}
