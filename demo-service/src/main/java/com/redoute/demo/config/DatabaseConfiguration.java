package com.redoute.demo.config;

import javax.sql.DataSource;

import org.hsqldb.jdbc.JDBCDataSource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Configuration for database (datasource, liquibase, ...)
 * @author ndeblock
 *
 */
@Configuration
@EnableJpaRepositories("com.redoute.demo.repository")
public class DatabaseConfiguration {


    // on peut conditionner l'init de la db en fonction de l'environnement
    // si on passe le paramètre -Sspring.profiles=dev, utiliser H2, sinon utiliser postgres par exemple
//    @ConditionalOnExpression("#{environment.acceptsProfiles('dev')}")
    public DataSource testDataSource() {

        // renvoie une datasource h2 pour le dev / les tests
        return new JDBCDataSource();
    }

}
