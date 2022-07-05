package com.company.stockchecker.schedule;

import com.company.stockchecker.bot.StockBot;
import com.company.stockchecker.config.SchedulingConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.service.*;
import com.company.stockchecker.util.MessageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StockScheduling {

    private final Logger logger = LoggerFactory.getLogger(StockScheduling.class);
    private final SchedulingConfig schedulingConfig;
    private final StockBot stockBot;
    private final IUserService userService;
    private final IStockService stockService;
    private final RestService restService;

    public StockScheduling(SchedulingConfig schedulingConfig, StockBot stockBot,
                           IUserService userService, IStockService stockService, RestService restService) {
        this.schedulingConfig = schedulingConfig;
        this.stockBot = stockBot;
        this.userService = userService;
        this.stockService = stockService;
        this.restService = restService;
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

    // every 5 minutes, is used to prevent heroku from putting application to sleep mode,
    @Scheduled(cron = "0 */5 * * * *", zone = "America/Sao_Paulo")
    private void pingSelf() {
        logger.info("Pinging heartbeat");
        this.restService.makeRequest(schedulingConfig.getAppUrl() + "/api/v1/heartbeat",
                "Connection", "keep-alive", String.class, HttpMethod.GET);
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
