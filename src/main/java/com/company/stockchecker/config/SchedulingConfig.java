package com.company.stockchecker.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@Getter
public class SchedulingConfig {

    //@Value("${app.url}")
   // private String appUrl;

}
