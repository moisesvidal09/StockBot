package com.company.stockchecker.service;

import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.util.StockUtil;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;

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

}
