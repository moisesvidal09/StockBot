package com.company.stockchecker.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class GreetingsConfig {

    @Value("${greetings.morning}")
    private String greetingsMorning;

    @Value("${greetings.evening}")
    private String greetingsEvening;


}
