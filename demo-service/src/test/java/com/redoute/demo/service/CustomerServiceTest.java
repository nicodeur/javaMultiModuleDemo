package com.redoute.demo.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

import java.util.Date;

import javax.validation.ConstraintViolationException;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.redoute.demo.conf.SpringTestForServiceConfiguration;
import com.redoute.demo.entity.Customer;
import com.redoute.demo.repository.CustomerRepository;

/**
 * An Unit test for Service example
 * @author ndeblock
 *
 */
@RunWith(SpringRunner.class)  // Signify we test with spring
@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={SpringTestForServiceConfiguration.class}) // configure spring for the test
public class CustomerServiceTest {

	// To test service, we mock the repository
	@MockBean
	private CustomerRepository customerRepository;
	
	// autowired the service to test
	@Autowired
	private CustomerService customerService;
	
	private final Customer CUSTOMER_OK_TO_SAVE = new Customer();
	private final Customer CUSTOMER_OK_SAVE = new Customer();
	
	private final Customer CUSTOMER_KO_TO_SAVE = new Customer();
	
	@Before
	public void initTest() {
		// Here initialise data for your test
		Date date = new Date();
		
		CUSTOMER_OK_SAVE.setId(1l);
		CUSTOMER_OK_SAVE.setFirstName("Toto");
		CUSTOMER_OK_SAVE.setLastName("Titi");
		CUSTOMER_OK_SAVE.setBirthDate(date);
		
		CUSTOMER_OK_TO_SAVE.setFirstName("Toto");
		CUSTOMER_OK_TO_SAVE.setLastName("Titi");
		CUSTOMER_OK_TO_SAVE.setBirthDate(date);
		
		CUSTOMER_KO_TO_SAVE.setFirstName("");
		CUSTOMER_KO_TO_SAVE.setLastName("Titi");
		CUSTOMER_KO_TO_SAVE.setBirthDate(date);
	}
	
	/**
	 * You have to test the nominal case for your method ...
	 */
    @Test
    public void saveCustomerWorksTest() {
        // 1st, RemoteService has been injected into the reverser bean
        given(this.customerRepository.save(CUSTOMER_OK_TO_SAVE)).willReturn(CUSTOMER_OK_SAVE);
        
        // 2nd, call the method to test. Try to test only one method by test method
        Customer customerReturned = customerService.saveCustomer(CUSTOMER_OK_TO_SAVE);
        
        // 3th, test if the result is correct
        assertThat(customerReturned).isEqualTo(CUSTOMER_OK_SAVE);
    }
    
	/**
	 * And you have to test all the errors cases for your method 
	 */
    @Test
    public void saveCustomerThrowConstraintViolationExceptionTest() {
        // 1st, RemoteService has been injected into the reverser bean
        given(this.customerRepository.save(CUSTOMER_KO_TO_SAVE)).willReturn(CUSTOMER_OK_SAVE);
        
        // 2nd, call the method to test. Try to test only one method by test method
        boolean constraintViolationExceptionOK=false;
        try {
        	customerService.saveCustomer(CUSTOMER_KO_TO_SAVE);
        } catch (ConstraintViolationException e) {
        	constraintViolationExceptionOK=true;
		}
        
        // 3th, if an ConstraintViolationException, test is ok
        assertTrue(" customerService.saveCustomer have to return a ConstraintViolationException",constraintViolationExceptionOK);
    }
}
