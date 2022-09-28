package com.epam.esm.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan("com.epam.esm")
@EnableTransactionManagement
//@EnableWebMvc
//@PropertySource(value = "classpath:application-dev.properties")
public class MyConfig {

    @Autowired
    private Environment environment;


//    @Bean(name = "messageSource")
//    public MessageSource getMessageSource() {
//        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
//        ret.setBasename("classpath:locale/message.properties");
//        ret.setCacheSeconds(1);
//        ret.setUseCodeAsDefaultMessage(true);
//        ret.setDefaultEncoding("utf-8");
//        return ret;
//    }
//
//    @Bean(name = "localeResolver")
//    public LocaleResolver localeResolver() {
//        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
//        resolver.setSupportedLocales(Arrays.asList(new Locale("en_US"), new Locale("ru_RU")));
//        resolver.setDefaultLocale(Locale.ENGLISH);
//        return resolver;
//    }

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(
                "com.epam.esm");
        sessionFactory.setHibernateProperties(properties());
        return sessionFactory;
    }



    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        HibernateTransactionManager transactionManager
                = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }


    @Bean
    public DataSource dataSource() {

        DriverManagerDataSource driverManager = new DriverManagerDataSource();

        driverManager.setUrl(environment.getRequiredProperty("spring.datasource.url"));
        driverManager.setUsername(environment.getRequiredProperty("spring.datasource.username"));
        driverManager.setPassword(environment.getRequiredProperty("spring.datasource.password"));
        driverManager.setDriverClassName(environment.getRequiredProperty("spring.datasource.driver-class-name"));
        return driverManager;
    }

    private Properties properties() {

        Properties properties = new Properties();

        properties.put("spring.jpa.properties.hibernate.dialect", environment.getRequiredProperty("spring.jpa.properties.hibernate.dialect"));
        properties.put("spring.jpa.show_sql", environment.getRequiredProperty("spring.jpa.show_sql"));
        properties.put("spring.jpa.properties.hibernate.format_sql", environment.getRequiredProperty("spring.jpa.properties.hibernate.format_sql"));
        properties.put("spring.jpa.hibernate.ddl-auto", environment.getRequiredProperty("spring.jpa.hibernate.ddl-auto"));

        return properties;
    }



}
