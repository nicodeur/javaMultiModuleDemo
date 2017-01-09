package com.redoute.demo.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.redoute.demo.conf.DatabaseTestConfiguration;
import com.redoute.demo.config.SpringConfiguration;
import com.redoute.demo.entity.Customer;
import com.redoute.demo.repository.CustomerRepository;

/**
 * A unit test for Repository example
 * @author ndeblock
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=WebEnvironment.MOCK, classes={SpringConfiguration.class, DatabaseTestConfiguration.class})
@DataJpaTest // configure jpa and the database for the test 
public class CustomerRepositoryTest {

	// To test service, we mock the repository
	@Autowired
	private CustomerRepository customerRepository;
	
	private final Customer CUSTOMER_OK_TO_SAVE = new Customer();
	
	@Before
	public void initTest() {
		// Here initialise data for your test
		Date date = new Date();
		
		CUSTOMER_OK_TO_SAVE.setFirstName("Toto");
		CUSTOMER_OK_TO_SAVE.setLastName("Titi");
		CUSTOMER_OK_TO_SAVE.setBirthDate(date);
	}
	
	/**
	 * test the save method 
	 */
    @Test
    public void saveCustomerWorksTest() {
        // 1nd, call the method to test. Try to test only one method by test method
        Customer customerReturned = customerRepository.save(CUSTOMER_OK_TO_SAVE);
        
        // 2th, test if the result is correct
        assertThat(customerReturned.getId()).isNotNull();
    }
    
}
