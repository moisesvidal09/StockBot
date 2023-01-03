package com.company.stockchecker.bot.command;

import com.company.stockchecker.exception.BotException;
import lombok.Getter;
import org.h2.util.StringUtils;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Getter
public enum CommandEnum {

    STOCK_ADD("/ADICIONAR"), STOCK_DELETE("/DELETAR"), STOCK_HELP("/AJUDA"), STOCK_GET("/CARTEIRA"),
    STOCK_ADD_HELP(STOCK_HELP.command + "_ADICIONAR"), STOCK_DELETE_HELP(STOCK_HELP.command + "_DELETAR"),
    STOCK_GET_HELP(STOCK_HELP.command + "_CARTEIRA");

    private final String command;

    CommandEnum(String command) {
        this.command = command;
    }

    public static CommandEnum getByText(String text) {
        if(StringUtils.isNullOrEmpty(text))
            return STOCK_HELP;

        String commandsSeparateByVerticalDash = Arrays.stream(CommandEnum.values())
                .map(CommandEnum::getCommand)
                .map(command -> command.replace("/","\\/"))
                .sorted(Comparator.comparingInt(String::length).reversed())
                .collect(Collectors.joining("|"));

        String regex = "^("+commandsSeparateByVerticalDash+")";

        Pattern commandPattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);

        Matcher commandMatcher = commandPattern.matcher(text);

        if(!commandMatcher.find())
            return STOCK_HELP; //Change to : the Command doesn't exist

        return Arrays.stream(CommandEnum.values())
                    .filter(command -> command.getCommand().equalsIgnoreCase(commandMatcher.group()))
                    .findFirst()
                    .orElseThrow(() -> new BotException("Command "+commandMatcher.group()+" not fount !"));
    }

    public static CommandEnum getByText(Update update) {

        String command = Optional.ofNullable(update)
                        .map(updateBot -> updateBot.hasMessage() && updateBot.getMessage().hasText() ?
                                updateBot.getMessage().getText() : updateBot.hasCallbackQuery() ?
                                updateBot.getCallbackQuery().getData() : null)
                        .orElse("");

        return getByText(command);
    }

}
