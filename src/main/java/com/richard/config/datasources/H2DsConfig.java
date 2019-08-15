package com.richard.config.datasources;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;

@Configuration
@EnableJpaRepositories(
    entityManagerFactoryRef = "h2EntityManagerFactory",
    transactionManagerRef = "h2TransactionManager",
    basePackages = "com.richard.h2.repositories"
)
@EnableTransactionManagement
public class H2DsConfig {

    @Bean(name = "h2DataSourceProperties")
    @ConfigurationProperties(prefix = "spring.h2.datasource")
    public DataSourceProperties h2DataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "h2DataSource")
    @ConfigurationProperties(prefix = "spring.h2.datasource")
    public DataSource dataSource(@Qualifier("h2DataSourceProperties") DataSourceProperties properties) {
        return properties.initializeDataSourceBuilder().build();
    }

    @Bean(name = "h2EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean h2kEntityManagerFactory(EntityManagerFactoryBuilder builder,
                                                                           @Qualifier("h2DataSource") DataSource dataSource) {
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create-drop");
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        return builder.dataSource(dataSource)
                      .properties(properties)
                      .packages("com.richard.h2.domain")
                      .persistenceUnit("h2")
                      .build();
    }

    @Bean(name = "h2TransactionManager")
    public PlatformTransactionManager h2TransactionManager(
        @Qualifier("h2EntityManagerFactory") EntityManagerFactory h2kEntityManagerFactory) {
        return new JpaTransactionManager(h2kEntityManagerFactory);
    }

}
