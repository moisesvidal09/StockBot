package com.company.stockchecker.util;

import com.company.stockchecker.entity.dto.StockDTO;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class MessageUtil {

    private static final NumberFormat currencyBRLFormat = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));

    public static String buildMessageToStockNews(final List<StockDTO> stocksNews){

        StringBuilder sb = new StringBuilder("");
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        stocksNews.forEach(stock -> {

            sb.append(stock.getSymbol()).append("\n\n");

            if(Objects.isNull(stock.getNews()) || stock.getNews().isEmpty())
                sb.append("Sem novidades para essa ação").append("\n\n");

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

    public static String buildMessageStockPrice(final List<StockDTO> stocks){

        StringBuilder message = new StringBuilder();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        message.append(LocalDateTime.now().format(dateTimeFormatter));
        message.append("\n\n");

        stocks.forEach(stock -> {

            message.append(stock.getSymbol());
            message.append("\n\n");

            message.append("Open Price -> ");
            message.append(currencyBRLFormat.format(stock.getOpenDayPrice()));
            message.append("\n\n");

            message.append("Close Price -> ");
            message.append(currencyBRLFormat.format(stock.getCloseDayPrice()));
            message.append("\n\n");

            message.append("Low Price -> ");
            message.append(currencyBRLFormat.format(stock.getLowDayPrice()));
            message.append("\n\n");

            message.append("High Price -> ");
            message.append(currencyBRLFormat.format(stock.getHighDayPrice()));
            message.append("\n\n");
            message.append("\n\n");

        });

        return message.toString();
    }

}
