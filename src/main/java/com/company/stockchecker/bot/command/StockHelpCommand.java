package com.company.stockchecker.bot.command;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Component
public class StockHelpCommand implements Command{

    @Override
    public String handleRequest(Update update) {
        return helpText(update);
    }

    private String helpText(Update update) {

        String firstName = update.getMessage().getFrom().getFirstName();

        return "Olá " + firstName + ", selecione uma das opções abaixo para obter ajuda :)";
    }

    public static void buildHelpButtons(SendMessage message){

        if(Objects.isNull(message))
            return;

        InlineKeyboardButton buttonAddStock = InlineKeyboardButton.builder()
                .text("Adicionar Ação")
                .callbackData(CommandEnum.STOCK_ADD_HELP.getCommand())
                .build();
        InlineKeyboardButton buttonRemoveStock = InlineKeyboardButton.builder()
                .text("Deletar Ação")
                .callbackData(CommandEnum.STOCK_DELETE_HELP.getCommand())
                .build();

        List<InlineKeyboardButton> row1 = new ArrayList<>(Arrays.asList(buttonAddStock, buttonRemoveStock));

        InlineKeyboardButton buttonWallet = InlineKeyboardButton.builder()
                .text("Carteira")
                .callbackData(CommandEnum.STOCK_GET_HELP.getCommand())
                .build();

        List<InlineKeyboardButton> row2 = new ArrayList<>(Arrays.asList(buttonWallet));

        List<List<InlineKeyboardButton>> buttons = new ArrayList<>(Arrays.asList(row1,row2));

        // Create the inline keyboard
        InlineKeyboardMarkup markup = new InlineKeyboardMarkup();
        markup.setKeyboard(buttons);

        // Set the inline keyboard as the reply markup for the message
        message.setReplyMarkup(markup);
    }
}
