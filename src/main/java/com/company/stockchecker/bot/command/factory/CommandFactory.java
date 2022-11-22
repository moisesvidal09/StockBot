package com.company.stockchecker.bot.command.factory;

import com.company.stockchecker.bot.command.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class CommandFactory {

    private final StockHelpCommand stockHelpCommand;
    private final StockAddCommand stockAddCommand;
    private final StockGetCommand stockGetCommand;
    private final StockDeleteCommand stockDeleteCommand;
    private final Map<CommandEnum, Command> commands = new HashMap<>();


    public CommandFactory(StockHelpCommand stockHelpCommand, StockAddCommand stockAddCommand, StockGetCommand stockGetCommand, StockDeleteCommand stockDeleteCommand) {
        this.stockHelpCommand = stockHelpCommand;
        this.stockAddCommand = stockAddCommand;
        this.stockGetCommand = stockGetCommand;
        this.stockDeleteCommand = stockDeleteCommand;
        initializeMap();
    }

     private void initializeMap(){

        commands.put(CommandEnum.STOCK_HELP, stockHelpCommand);
        commands.put(CommandEnum.STOCK_ADD, stockAddCommand);
        commands.put(CommandEnum.STOCK_GET, stockGetCommand);
        commands.put(CommandEnum.STOCK_DELETE, stockDeleteCommand);

    }

    public Command getCommand(CommandEnum commandEnum){
        return Optional.ofNullable(this.commands.get(commandEnum)).
                orElseThrow(() -> new IllegalArgumentException("Command "+ commandEnum + " not found !"));
    }

}
