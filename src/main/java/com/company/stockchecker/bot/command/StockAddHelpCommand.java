package com.company.stockchecker.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StockAddHelpCommand implements Command{

    @Override
    public String handleRequest(Update update) {
        return addStockHelpMessage();
    }

    private String addStockHelpMessage(){
        return "Para adicionar uma ação digite o comando /adicionar seguido do código da ação.\n\n"
                + "Exemplo: /adicionar BBAS3\n\n"
                + "Para adicionar mais de uma ação de uma vez, digite o código das ações separados por vírgula.\n\n"
                + "Exempo: /adicionar BBAS3, ITUB4";
    }

}
