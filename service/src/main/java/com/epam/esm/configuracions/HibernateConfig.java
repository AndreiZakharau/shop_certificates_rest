package com.epam.esm.configuracions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@EnableWebMvc
@PropertySource(value = "classpath:hibernate.properties")
public class HibernateConfig {

    private Environment environment;

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean localSessionFactoryBean = new LocalSessionFactoryBean();

        localSessionFactoryBean.setPackagesToScan("com.epam.esm.entity");
        localSessionFactoryBean.setDataSource(dataSource());
        localSessionFactoryBean.setHibernateProperties(properties());

        return localSessionFactoryBean;
    }

    @Bean
    public DataSource dataSource(){

        DriverManagerDataSource driverManager= new DriverManagerDataSource();

        driverManager.setUrl(environment.getRequiredProperty("hibernate.url"));
        driverManager.setUsername(environment.getRequiredProperty("hibernate.username"));
        driverManager.setPassword(environment.getRequiredProperty("hibernate.password"));
        driverManager.setDriverClassName(environment.getRequiredProperty("hibernate.driver"));
        return driverManager;
    }

    @Bean
    public HibernateTransactionManager transactionManager() {

        HibernateTransactionManager transactionManager = new HibernateTransactionManager();

        transactionManager.setSessionFactory(sessionFactory().getObject());
        return  transactionManager;
    }

    private Properties properties() {

        Properties properties = new Properties();

        properties.put("hibernate.dialect",environment.getRequiredProperty("hibernate.dialect"));
        properties.put("hibernate.show_sql",environment.getRequiredProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql",environment.getRequiredProperty("hibernate.format_sql"));
        properties.put("hibernate.hbm2ddl.auto",environment.getRequiredProperty("hibernate.hbm2ddl.auto"));

        return properties;
    }
}
