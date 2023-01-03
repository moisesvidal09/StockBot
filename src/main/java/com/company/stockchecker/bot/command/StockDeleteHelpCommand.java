package com.company.stockchecker.bot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StockDeleteHelpCommand implements Command{
    @Override
    public String handleRequest(Update update) {
        return getDeleteStockMessage();
    }

    private String getDeleteStockMessage(){
        return "Para deletar uma ação digite o comando /deletar seguido do código da ação.\n\n"
                + "Exemplo: /deletar BBAS3\n\n"
                + "Para deletar mais de uma ação de uma vez, digite o código das ações separados por vírgula.\n\n"
                + "Exempo: /deletar BBAS3, ITUB4";
    }

}
