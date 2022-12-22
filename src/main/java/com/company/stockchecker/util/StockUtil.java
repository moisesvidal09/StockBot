package com.company.stockchecker.util;

import com.company.stockchecker.entity.dto.StockDTO;
import io.netty.util.internal.StringUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class StockUtil {

    private static final String SOUTH_AMERICA_SUFFIX = ".SA";

    public static String addSouthAmericaSuffix(String stockCode){
        if(StringUtil.isNullOrEmpty(stockCode))
            return "";

        return stockCode.endsWith(SOUTH_AMERICA_SUFFIX) ? stockCode : stockCode.concat(SOUTH_AMERICA_SUFFIX);
    }

    public static Map<String, StockDTO> sortMapByDateKey(Map<String, StockDTO> stockByDate){
        if(Objects.isNull(stockByDate) || stockByDate.isEmpty())
            return new HashMap<>();

        Map<String, StockDTO> sortedMap = new TreeMap<>((date1, date2) -> {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date d1 = format.parse(date1);
                    Date d2 = format.parse(date2);
                    return d1.compareTo(d2);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            });

        sortedMap.putAll(stockByDate);
        return sortedMap;
    }

}
