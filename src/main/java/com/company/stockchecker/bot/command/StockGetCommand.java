package com.company.stockchecker.bot.command;

import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.service.UserService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.stream.Collectors;

@Component
public class StockGetCommand implements Command{

    private final UserService userService;

    public StockGetCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handleRequest(Update update) {

        User user =  userService.getByChatId(update.getMessage().getChatId());

        String stocks = user
                .getStocks()
                .stream()
                .map(Stock::getCode)
                .collect(Collectors.joining(","));

        return "Suas ações: " + stocks;
    }
}
