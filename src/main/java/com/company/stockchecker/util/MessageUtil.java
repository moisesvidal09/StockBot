package com.company.stockchecker.util;

import com.company.stockchecker.entity.dto.StockDTO;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class MessageUtil {

    public static String buildMessageToStockNews(final List<StockDTO> stocksNews){

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
