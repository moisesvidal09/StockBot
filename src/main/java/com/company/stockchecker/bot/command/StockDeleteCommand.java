package com.company.stockchecker.bot.command;

import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.exception.BotException;
import com.company.stockchecker.service.UserService;
import com.google.common.base.Strings;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Component
public class StockDeleteCommand implements Command{

    private final UserService userService;

    public StockDeleteCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String handleRequest(Update update) {

        String stocksFromMessage = update.getMessage().getText().toUpperCase(Locale.ROOT).trim();

        stocksFromMessage = stocksFromMessage.replace(CommandEnum.STOCK_DELETE.getCommand(), "").trim();

        if (Strings.isNullOrEmpty(stocksFromMessage))
            throw new BotException("Was not possible to identify stocks requested");


        List<String> stocksToDelete = Arrays.asList(stocksFromMessage.split(","));

        User user = this.userService.getByChatId(update.getMessage().getChatId());

        BiPredicate<Stock, List<String>> userStockContainsStocksRequested = (userStock, stockToDeleteList) -> stockToDeleteList.contains(userStock.getCode());

        user.getStocks().removeIf(userStock -> userStockContainsStocksRequested.test(userStock, stocksToDelete));

        StringBuilder response = new StringBuilder();

        userService.update(user);

        response.append("Sua carteira: ");

        response.append("\n\n");

        response.append(String.join("\n", user.getStocks().stream().map(Stock::getCode).collect(Collectors.toSet())));

        return response.toString();
    }



}
