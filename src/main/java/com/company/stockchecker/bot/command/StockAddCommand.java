package com.company.stockchecker.bot.command;


import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.exception.BotException;
import com.company.stockchecker.service.IUserService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class StockAddCommand implements Command {

    private final IUserService userService;

    public StockAddCommand(IUserService userService) {
        this.userService = userService;
    }


    private List<String> retrieveStocksFromUserText(Update update){

        String text = update.getMessage().getText().toUpperCase(Locale.ROOT).trim();

        text = text.replace(CommandEnum.STOCK_ADD.getCommand(), "").trim();

        if ("".equalsIgnoreCase(text))
             throw new BotException("Was not possible to identify stocks requested");

        return Arrays.stream(text.split(","))
                .map(String::trim)
                .collect(Collectors.toList());
    }

    @Override
    public String handleRequest(Update update) {

        List<String> stocksRequested = this.retrieveStocksFromUserText(update);

        Set<Stock> stocks = stocksRequested.stream()
                                            .map(stockCode -> Stock.builder()
                                                    .code(stockCode)
                                                    .build())
                                            .collect(Collectors.toSet());

        this.saveUser(update, stocks);

        String firstName = update.getMessage().getFrom().getFirstName();

        return firstName + ", foram adicionadas as ações "
                + Strings.join(stocksRequested, ',') + " com sucesso !"  ;
    }

    private void saveUser(Update update, Set<Stock> stocks){

        User user = User.builder()
                .chatId(update.getMessage().getChatId())
                .stocks(stocks)
                .build();

        boolean userNotExistsByChatId = !userService.existsByChatId(user.getChatId());

        if (userNotExistsByChatId)
             userService.create(user);
        else {
            user = userService.getByChatId(user.getChatId());
            user.getStocks().addAll(stocks);
            userService.update(user);
        }
    }
}
