package com.company.stockchecker.bot;

import com.company.stockchecker.exception.BotException;
import com.company.stockchecker.bot.command.Command;
import com.company.stockchecker.bot.command.CommandEnum;
import com.company.stockchecker.bot.command.factory.CommandFactory;
import com.company.stockchecker.config.TelegramConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.persistence.EntityNotFoundException;
import java.util.logging.Logger;

@Component
public class StockBot extends TelegramLongPollingBot {

    private final TelegramConfig telegramConfig;
    private final CommandFactory commandFactory;

    private static final Logger logger = Logger.getGlobal();

    public StockBot(TelegramConfig telegramConfig, CommandFactory commandFactory) {
        this.telegramConfig = telegramConfig;
        this.commandFactory = commandFactory;
    }

    @Override
    public String getBotUsername() {
        return telegramConfig.getBotName();
    }

    @Override
    public String getBotToken() {
        return telegramConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String chatId = String.valueOf(update.getMessage().getChatId());
            String response;

            try {

                CommandEnum commandEnum = CommandEnum.getByText(update);
                Command commandRequested = commandFactory.getCommand(commandEnum);
                response = commandRequested.handleRequest(update);

            } catch (BotException e){

                logger.warning("Error processing request " + e.getMessage());
                response = e.getMessage();

            } catch (EntityNotFoundException e) {

                logger.warning("Error processing request " + e.getMessage());
                response = "Você não tem ações cadastras, tente /ajuda.";
            }

            sendMessageTo(response, chatId);
        }
    }

    public void sendMessageTo(String message, String chatId) {
        SendMessage msg = new SendMessage();
        msg.setText(message);
        msg.setChatId(chatId);
        try {
            execute(msg);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.warning("Something went wrong when trying to sent an message to chatId = " + chatId + " " + e.getMessage());
        }
    }

}
