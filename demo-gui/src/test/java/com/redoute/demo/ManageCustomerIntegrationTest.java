package com.redoute.demo;

import static org.junit.Assert.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.redoute.demo.controller.ManageCustomerController;
import com.redoute.demo.entity.Customer;

/**
 * Example of Integration Test. Integration test test all modules of the application (no mock)
 * Integration Test don't test the view, see cerberus to make it. 
 * @author ndeblock
 *
 */
@RunWith(SpringRunner.class) // Signify we test with spring
@WebMvcTest(controllers = ManageCustomerController.class) // configure automatically the test (init conf spring)
@AutoConfigureDataJpa // configure jpa
@AutoConfigureTestDatabase // configure a database automatically
public class ManageCustomerIntegrationTest {

	@Autowired
	private WebApplicationContext context;

	// allow to make request for our controller
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				//.apply(springSecurity()) 
				.build();
	}
	
	/**
	 * Test a get method
	 * @throws Exception 
	 */
	@Test
	public void manageCustomerGetTest() throws Exception {
		ResultActions response = mvc.perform(get("/manageCustomer"));

		MvcResult mvcResult =  response
				.andExpect(status().isOk())
				.andExpect(view().name("manageCustomer"))
				.andExpect(model().attributeExists("customer")) // test if attribute is send
				.andDo(print())
				.andReturn();

		String content = mvcResult.getResponse().getContentAsString();
		System.out.println(content);

	}
	
	/**
	 * Test a post method with error
	 * @throws Exception 
	 */
	@Test
	public void manageCustomerPostTestWithError() throws Exception {
		// 1rst construct the ko customer
		Customer c = new Customer();
		c.setFirstName("bla");

		// call  the controller, it test all (database, repository, service and controller)
		mvc.perform(post("/manageCustomer",c))
				.andExpect(status().isOk())
				.andExpect(view().name("manageCustomer")) // test name of called view 
				.andExpect(model().attributeExists("customer")) // test if attribute is send
				.andExpect(model().attributeHasErrors("customer")) // test if attributhave error
				.andExpect(model().attributeHasFieldErrorCode("customer", "birthDate","NotNull")) // test if it's the correct error on field birthDate of attribute customer
				.andExpect(model().attributeHasFieldErrorCode("customer", "lastName", "NotNull")) // test if it's the correct error on field lastName of attribute customer
				//.andDo(print()) // print the result to console, util to construct test
				;
	}

	/**
	 * C complete test : add a customer via POST controller, and display it with GET controller
	 * @throws Exception 
	 */
	@Test
	public void manageCustomerPostTestSucess() throws Exception {
		// 1rst construct the ko customer
		Customer customer = new Customer();
		customer.setFirstName("bla");
		customer.setLastName("bla");
		customer.setBirthDate(java.sql.Date.valueOf(LocalDate.now()));

		// 2nd call  the controller, it test all (database, repository, service and controller)
		MvcResult mvcResult = mvc.perform(post("/manageCustomer").flashAttr("customer", customer))
				.andExpect(status().isOk())
				.andExpect(view().name("manageCustomer")) // test name of called view 
				.andExpect(model().attributeExists("customer")) // test if attribute is send
				//.andDo(print()) // print the result to console, util to construct test
				.andReturn();
		
		// 3rd Test if customer had really add to database
		Customer customerReturned = (Customer) mvcResult.getModelAndView().getModel().get("customer");
		if(customerReturned.getId() == null) {
			fail();
		}
		
		mvc.perform(get("/manageCustomer").param("idCustomer", customerReturned.getId().toString()))
				.andExpect(status().isOk())
				.andExpect(view().name("manageCustomer"))
				.andExpect(model().attributeExists("customer")) // test if attribute is send
				.andExpect(model().attribute("customer",customerReturned)) // test if the good customer is returned to view
				.andReturn();
		
	}

}
