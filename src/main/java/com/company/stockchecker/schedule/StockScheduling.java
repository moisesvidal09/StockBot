package com.company.stockchecker.schedule;

import com.company.stockchecker.bot.StockBot;
import com.company.stockchecker.config.GreetingsConfig;
import com.company.stockchecker.config.SchedulingConfig;
import com.company.stockchecker.config.TelegramConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StockScheduling {

    private final Logger logger = LoggerFactory.getLogger(StockScheduling.class);
    private final TelegramConfig telegramConfig;
    private final GreetingsConfig greetingsConfig;
    private final SchedulingConfig schedulingConfig;
    private final StockBot stockBot;

    public StockScheduling(TelegramConfig telegramConfig, GreetingsConfig greetingsConfig, SchedulingConfig schedulingConfig,
                           StockBot stockBot) {
        this.telegramConfig = telegramConfig;
        this.greetingsConfig = greetingsConfig;
        this.schedulingConfig = schedulingConfig;
        this.stockBot = stockBot;
    }

    // 18:00 every day
    @Scheduled(cron = "0 0 18 * * *", zone = "America/Sao_Paulo")
    private void notifyEightTeenHourEveryDay(){

    }
}
