package com.redoute.demo.config;

import javax.sql.DataSource;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
public class LiquibaseConfiguration  implements EnvironmentAware{

    private RelaxedPropertyResolver liquiBasePropertyResolver;

    @Override
	public void setEnvironment(Environment env) {
        this.liquiBasePropertyResolver = new RelaxedPropertyResolver(env, "liquiBase.");
    }
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:liquibase/db.changelog-master.xml");
        liquibase.setContexts(liquiBasePropertyResolver.getProperty("context"));
        return liquibase;
    }

}
