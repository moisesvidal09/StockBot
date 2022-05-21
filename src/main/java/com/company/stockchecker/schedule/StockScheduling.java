package com.company.stockchecker.schedule;

import com.company.stockchecker.bot.StockBot;
import com.company.stockchecker.config.GreetingsConfig;
import com.company.stockchecker.config.SchedulingConfig;
import com.company.stockchecker.config.TelegramConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.service.StockService;
import com.company.stockchecker.service.UserService;
import com.company.stockchecker.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockScheduling {

    private final Logger logger = LoggerFactory.getLogger(StockScheduling.class);
    private final TelegramConfig telegramConfig;
    private final GreetingsConfig greetingsConfig;
    private final SchedulingConfig schedulingConfig;
    private final StockBot stockBot;
    private final UserService userService;
    private final StockService stockService;

    public StockScheduling(TelegramConfig telegramConfig, GreetingsConfig greetingsConfig, SchedulingConfig schedulingConfig,
                           StockBot stockBot, UserService userService, StockService stockService) {
        this.telegramConfig = telegramConfig;
        this.greetingsConfig = greetingsConfig;
        this.schedulingConfig = schedulingConfig;
        this.stockBot = stockBot;
        this.userService = userService;
        this.stockService = stockService;
    }

    // 08:00 every day
    @Scheduled(cron = "0 0 8 * * 1-5", zone = "America/Sao_Paulo")
    private void notifyEightTeenHourEveryDay(){
        List<User> users = this.userService.getAll();

        logger.info("Starting thread for all users");

        Set<String> stocks = this.stockService.findAllDistinctCode();

        List<StockDTO> stocksNews = this.stockService.getStocksNews(new ArrayList<>(stocks));

        new Thread(() -> sentStockNewsToAllUsers(users, stocksNews)).start();
    }

    private void sentStockNewsToAllUsers(final List<User> users, final List<StockDTO> stocksNews){

        logger.info("Thread started");

        users.parallelStream()
                .forEach(user -> {

                        List<String> stocksUserCode = user.getStocks()
                                                            .stream()
                                                            .map(Stock::getCode)
                                                            .collect(Collectors.toList());

                        List<StockDTO> stockUserNews = stocksNews.parallelStream()
                                                                    .filter(stockNews -> stocksUserCode.contains(stockNews.getSymbol()))
                                                                    .collect(Collectors.toList());

                        String messageToUser = MessageUtil.buildMessageToStockNews(stockUserNews);

                        this.stockBot.sendMessageTo(messageToUser, String.valueOf(user.getChatId()));
                });

    }
}
