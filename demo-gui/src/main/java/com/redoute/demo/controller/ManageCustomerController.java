package com.redoute.demo.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import com.redoute.demo.entity.Customer;
import com.redoute.demo.service.CustomerService;
import com.redoute.demo.service.exception.CustomerNotFoundException;

@Controller
public class ManageCustomerController {
	

	
	@Autowired
	private CustomerService customerService;
	
	 @InitBinder
	 public void initBinder(WebDataBinder binder) {
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 sdf.setLenient(true);
		 binder.registerCustomEditor(Date.class, new CustomDateEditor(sdf, true));
	 }

    @RequestMapping(value={"/","/manageCustomer"}, method=RequestMethod.GET)
    public String manageCustomerGet(Long idCustomer, Model model) {
    	
    	if(idCustomer == null) {
    		model.addAttribute("customer", new Customer());
    	} else {
    		try {
				model.addAttribute("customer", customerService.findCustomer(idCustomer));
			} catch (CustomerNotFoundException e) {
				// here, we display the error to screen
				model.addAttribute("errorMsg", "We can't found customer");
				return "displayError"; // return to an error page
			}
    	}
    	
        return "manageCustomer";
    }
    
    @RequestMapping(value={"/","/manageCustomer"}, method=RequestMethod.POST)
    public String manageCustomerPost(Model model, @Valid Customer customer, BindingResult result) {
    	
    	if (result.hasErrors()) {
    		model.addAttribute("customer", customer);
    	} else {
    		Customer customerSaved = customerService.saveCustomer(customer);
    		model.addAttribute("successMsg", "We stored the user, id " + customerSaved.getId() +". you can test to modify it hith url <a href=\"manageCustomer?idCustomer=" + customerSaved.getId() +"\">manageCustomer?idCustomer=" + customerSaved.getId() +"</a>");
    	}
    	
    	model.addAttribute("customer", customer);
       
        return "manageCustomer";
    }
}
