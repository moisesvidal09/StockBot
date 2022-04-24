package com.company.stockchecker.bot.command;

import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.service.StockService;
import com.company.stockchecker.service.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Component
public class StockGetCommand implements Command{

    private final UserService userService;
    private final StockService stockService;

    public StockGetCommand(UserService userService, StockService stockService) {
        this.userService = userService;
        this.stockService = stockService;
    }

    @Override
    public String handleRequest(Update update) {

        User user =  userService.getByChatId(update.getMessage().getChatId());

        List<StockDTO> stocksNews = stockService.getStocksNews(
                user.getStocks()
                        .stream()
                        .map(Stock::getCode)
                        .collect(Collectors.toList())
        );

        StringBuilder sb = new StringBuilder("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        stocksNews.forEach(stock -> {

            sb.append(stock.getSymbol()).append("\n\n");

                stock.getNews()
                        .forEach(news -> {

                            sb.append("Título: ").append(news.getTitle()).append("\n");
                            sb.append("Fonte: ").append(news.getSource().getName()).append("\n");
                            sb.append("Autor: ").append(news.getAuthor()).append("\n");
                            sb.append(news.getDescription()).append("\n");
                            sb.append(news.getPublishedAt().format(dateTimeFormatter)).append("\n");
                            sb.append(news.getUrl()).append("\n\n");

                        });

        });

        return sb.toString();
    }
}
