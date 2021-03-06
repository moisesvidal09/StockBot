package com.company.stockchecker.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:telegram.properties")
@Getter
public class TelegramConfig {


    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    @Value("${access.pwd}")
    private String password;

    @Value("${zone.id}")
    private String desiredZoneId;

    @Value("${general.id}")
    private String generalChatId;

}
