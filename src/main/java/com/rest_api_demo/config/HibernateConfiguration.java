package com.rest_api_demo.config;


import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource(value = {"classpath:application.properties"})
public class HibernateConfiguration {

  @Autowired
   Environment env;
    @Bean
    public DataSource dataSource(){
        DriverManagerDataSource dataSource=new DriverManagerDataSource();
        dataSource
                .setDriverClassName(env
                        .getProperty("spring.datasource.driver-class-name","com.mysql.cj.jdbc.Driver"));
        dataSource
                .setUrl(env
                        .getProperty("spring.datasource.url","jdbc:mysql://localhost:3306/product_manager"));
        dataSource
                .setUsername(env
                        .getProperty("spring.datasource.username", "root"));
        dataSource
                .setPassword(env
                        .getProperty("spring.datasource.password",""));
        return dataSource;
    }

    @Primary
    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("com.rest_api_demo.domain");
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(hibernateProperties());
        return em;
    }

    @Bean
    public EntityManager entityManager(LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean) {
        EntityManager em = localContainerEntityManagerFactoryBean
                .getNativeEntityManagerFactory()
                .createEntityManager();

        return em;
    }


    Properties hibernateProperties(){
        Properties properties=new Properties();
        properties
                .setProperty("hibernate.ddl-auto", env
                        .getProperty("spring.jpa.hibernate.ddl-auto","none"));
        properties
                .setProperty("hibernate.dialect", env
                        .getProperty("spring.jpa.properties.hibernate.dialect","org.hibernate.dialect.MySQLDialect"));
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {

        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return txManager;
    }


}
