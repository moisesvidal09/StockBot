package com.company.stockchecker.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StockGetHelpCommand implements Command{
    @Override
    public String handleRequest(Update update) {
        return getStockGetHelpCommand();
    }

    private String getStockGetHelpCommand(){
        return "Para buscar os preços das ações da sua carteira digite o comando /carteira";
    }
}
