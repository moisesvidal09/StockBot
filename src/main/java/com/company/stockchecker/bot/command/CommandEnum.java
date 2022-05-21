package com.company.stockchecker.bot.command;

import com.company.stockchecker.exception.BotException;
import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public enum CommandEnum {

    STOCK_ADD("/ADICIONAR"), STOCK_DELETE("/DELETAR"), STOCK_HELP("/AJUDA"), STOCK_GET("/CARTEIRA");

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public static CommandEnum getByText(String text) {
        for(CommandEnum type : values()) {
            if(type.getCommand().equalsIgnoreCase(text)) {
                return type;
            }
        }
        return null;
    }

    public static CommandEnum getByText(Update update) {
        if(!update.hasMessage())
            throw new BotException("No message");
        if(!update.getMessage().hasEntities() || update.getMessage().getEntities().isEmpty())
            throw new BotException("It doesn't look like a command for me");

        String command = update.getMessage()
                .getEntities()
                .stream()
                .findFirst().get().getText();

        return getByText(command);
    }

}
