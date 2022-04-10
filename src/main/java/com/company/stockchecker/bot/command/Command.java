package com.company.stockchecker.bot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
    String handleRequest(Update update);
}
