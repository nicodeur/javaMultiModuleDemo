package com.redoute.demo.config;

import org.springframework.context.annotation.*;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

/**
 * All spring configuration
 * @author ndeblock
 *
 */
@Configuration
@ComponentScan("com.redoute.demo")
public class SpringConfiguration {
	
	@Bean
	public LocalValidatorFactoryBean getValidator() {
		return new LocalValidatorFactoryBean();
	}
}
