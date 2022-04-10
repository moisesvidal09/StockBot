package com.company.stockchecker.bot.command;


import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.service.StockService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;


@Component
public class StockAddCommand implements Command {

    private final StockService stockService;

    public StockAddCommand(StockService stockService) {
        this.stockService = stockService;
    }

    public String findAllStockRequested(Update update) {

        String text = update.getMessage().getText().toUpperCase(Locale.ROOT).trim();

        String stocksRequested = text.replace(CommandEnum.STOCK_ADD.getCommand(), "").trim();

        List<String> stocks = Arrays.stream(stocksRequested.split(","))
                .map(String::trim)
                .collect(Collectors.toList());

        List<StockDTO> stockDTOS = stockService.getStocksFromAPI(stocks);

        return stockDTOS.stream()
                .map(s -> s.getSymbol() + " - " + s.getRegularMarketPrice() + s.getCurrency() + "\n")
                .collect(Collectors.joining());
    }

    @Override
    public String handleRequest(Update update) {
        return findAllStockRequested(update);
    }
}
