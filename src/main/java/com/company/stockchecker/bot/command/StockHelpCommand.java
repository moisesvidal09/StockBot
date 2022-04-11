package com.company.stockchecker.bot.command;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StockHelpCommand implements Command{

    @Override
    public String handleRequest(Update update) {
        return helpText(update);
    }


    private String helpText(Update update) {

        String firstName = update.getMessage().getFrom().getFirstName();

        return firstName + ", para adicionar ações a sua carteira basta digita \"/adicionar\" " +
                " mais o código das ações separando por vírgula ( , ). " +
                "\n\nExemplo: /adicionar BBAS3.SA, MGLU3.SA ...";
    }
}
