package com.challenge.sales.statistics.salesstatistics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.time.Clock;

@Configuration
public class Config {

    @Bean
    public LocalValidatorFactoryBean validator() {
        return new LocalValidatorFactoryBean();
    }

    /**
     * Clock bean to use all over the application.
     * External dependency, can be mocked in tests.
     * @return clock
     */
    @Bean
    Clock setClock() {
        return Clock.systemDefaultZone();
    }

}
