package com.company.stockchecker.service;

import com.company.stockchecker.bot.command.CommandEnum;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.util.StockUtil;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@ActiveProfiles("test")
public class StockUtilTest {

    @Test
    public void testSortMapByDateKey_emptyMap() {
        Map<String, StockDTO> emptyMap = new HashMap<>();
        Map<String, StockDTO> sortedEmptyMap = StockUtil.sortMapByDateKey(emptyMap);
        assertTrue(sortedEmptyMap.isEmpty());
    }

    @Test
    public void testSortMapByDateKey_oneEntryMap() {
        Map<String, StockDTO> oneEntryMap = new HashMap<>();
        oneEntryMap.put("2022-01-01", new StockDTO());
        Map<String, StockDTO> sortedOneEntryMap = StockUtil.sortMapByDateKey(oneEntryMap);
        assertEquals(1, sortedOneEntryMap.size());
        assertEquals("2022-01-01", sortedOneEntryMap.keySet().iterator().next());
    }

    @Test
    public void testSortMapByDateKey_multipleEntriesMap() {
        Map<String, StockDTO> multipleEntriesMap = new HashMap<>();
        multipleEntriesMap.put("2022-01-03", new StockDTO());
        multipleEntriesMap.put("2022-01-01", new StockDTO());
        multipleEntriesMap.put("2022-01-02", new StockDTO());
        Map<String, StockDTO> sortedMultipleEntriesMap = StockUtil.sortMapByDateKey(multipleEntriesMap);
        assertEquals(3, sortedMultipleEntriesMap.size());
        assertEquals("2022-01-03", new TreeMap<>(sortedMultipleEntriesMap).lastEntry().getKey());
    }

    @Test
    public void shouldReturnStockAddCommandFromMessage() {
        Update update = new Update();
        Message message = new Message();
        message.setText(CommandEnum.STOCK_ADD.getCommand());
        update.setMessage(message);
        CommandEnum stockAddCommand = CommandEnum.getByText(update);
        assertEquals(CommandEnum.STOCK_ADD, stockAddCommand);
    }

    @Test
    public void shouldReturnStockAddHelpCommandFromCallbackQuery() {
        Update update = new Update();
        CallbackQuery callbackQuery = new CallbackQuery();
        callbackQuery.setData(CommandEnum.STOCK_ADD_HELP.getCommand());
        update.setCallbackQuery(callbackQuery);
        CommandEnum stockAddHelpCommand = CommandEnum.getByText(update);
        assertEquals(CommandEnum.STOCK_ADD_HELP, stockAddHelpCommand);
    }

    @Test
    public void shouldReturnStockHelpWhenUpdateIsNull() {
        Update update = null;
        assertEquals(CommandEnum.STOCK_HELP, CommandEnum.getByText(update));
    }

    @Test
    public void shouldReturnStockHelpWhenMessageAndCallbackQueryIsNull() {
        Update update = new Update();
        assertEquals(CommandEnum.STOCK_HELP, CommandEnum.getByText(update));
    }

}
