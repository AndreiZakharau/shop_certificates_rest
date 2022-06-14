package com.epam.esm.configurations;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
@ComponentScan("com.epam.esm")
//@EnableWebMvc
//@PropertySource(value = "classpath:application-dev.properties")
public class WebConfig {

    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("classpath:locale/message");
        ret.setCacheSeconds(1);
        ret.setUseCodeAsDefaultMessage(true);
        ret.setDefaultEncoding("utf-8");
        return ret;
    }

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale("en_US"), new Locale("ru_RU")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }

//    @Bean
//    public LocalSessionFactoryBean getSessionFactory(@Autowired DataSource dataSource) {
//     LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//     sessionFactory.setDataSource(dataSource);
//     sessionFactory.setPackagesToScan("com.epam.esm.entity");
//     return sessionFactory;
//    }

}
