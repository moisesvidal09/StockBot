package com.company.stockchecker.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class StockConfig {

    @Value("${stock.url}")
    private String stockUrl;

    @Value("${api.key}")
    private String apiKey;

}
