package com.redoute.demo.conf;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories("com.redoute.demo.repository")
@EntityScan(basePackages={"com.redoute.demo.entity"})
public class DatabaseTestConfiguration implements EnvironmentAware {

    private RelaxedPropertyResolver liquiBasePropertyResolver;

    private Environment env;

    public void setEnvironment(Environment env) {
        this.env = env;
        this.liquiBasePropertyResolver = new RelaxedPropertyResolver(env, "liquiBase.");
    }

    // on peut conditionner l'init de la db en fonction de l'environnement
    // si on passe le paramètre -Sspring.profiles=dev, utiliser H2, sinon utiliser postgres par exemple
//    @ConditionalOnExpression("#{environment.acceptsProfiles('dev')}")
    public DataSource testDataSource() {

        // renvoie une datasource h2 pour le dev / les tests
        return new JDBCDataSource();
    }
/*
    @Bean
    public SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog("classpath:config/liquibase/master.xml");
        liquibase.setContexts(liquiBasePropertyResolver.getProperty("context"));
        return liquibase;
    }
*/
}
