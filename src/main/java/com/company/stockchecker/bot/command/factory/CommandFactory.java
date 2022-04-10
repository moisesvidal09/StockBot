package com.company.stockchecker.bot.command.factory;

import com.company.stockchecker.bot.command.Command;
import com.company.stockchecker.bot.command.CommandEnum;
import com.company.stockchecker.bot.command.StockAddCommand;
import com.company.stockchecker.bot.command.StockHelpCommand;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandFactory {

    private final StockHelpCommand stockHelpCommand;
    private final StockAddCommand stockAddCommand;
    private Map<CommandEnum, Command> commands = new HashMap<>();


    public CommandFactory(StockHelpCommand stockHelpCommand, StockAddCommand stockAddCommand) {
        this.stockHelpCommand = stockHelpCommand;
        this.stockAddCommand = stockAddCommand;
    }

     private void initializeMap(){

        commands.put(CommandEnum.STOCK_HELP, stockHelpCommand);
        commands.put(CommandEnum.STOCK_ADD, stockAddCommand);

    }

    public Command getCommand(CommandEnum commandEnum){
        return Optional.ofNullable(getInstance().get(commandEnum)).
                orElseThrow(() -> new IllegalArgumentException("Command "+ commandEnum + " not found !"));
    }

    private Map<CommandEnum, Command> getInstance(){

         if (this.commands.isEmpty())
              initializeMap();

         return this.commands;
    }

}
