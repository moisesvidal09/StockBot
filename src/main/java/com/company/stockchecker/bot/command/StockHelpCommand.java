package com.company.stockchecker.bot.command;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

@Data
@Component
public class StockHelpCommand implements Command{

    @Override
    public String handleRequest(Update update) {
        return helpText(update);
    }


    public String helpText(Update update) {

        String firstName = update.getMessage().getFrom().getFirstName();

        return firstName + ", para adicionar ações a sua carteira basta inserir o código delas separando por vírgula ( , ). " +
                "\n\nExemplo: BBAS3.SA, MGLU3.SA ...";
    }
}
