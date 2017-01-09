package com.redoute.demo.conf;

import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * Initialise Spring configuration with scan only the service package (mock others please)
 * @author ndeblock
 *
 */
@Configuration
@ComponentScan("com.redoute.demo.service")
public class SpringTestForServiceConfiguration {
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		return new LocalValidatorFactoryBean();
	}
}
