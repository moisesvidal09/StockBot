package com.company.stockchecker.bot.command;

import com.company.stockchecker.entity.User;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.service.IStockService;
import com.company.stockchecker.service.IUserService;
import com.company.stockchecker.util.MessageUtil;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Set;

@Component
public class StockGetCommand implements Command{

    private final IUserService userService;
    private final IStockService stockService;

    public StockGetCommand(IUserService userService, IStockService stockService) {
        this.userService = userService;
        this.stockService = stockService;
    }

    @Override
    public String handleRequest(Update update) {

        User user =  userService.getByChatId(update.getMessage().getChatId());

        Set<String> stocksCode = user.getStocksCode();

        List<StockDTO> stock = stockService.getStocksPrice(stocksCode);

        return MessageUtil.buildMessageStockPrice(stock);
    }
}
