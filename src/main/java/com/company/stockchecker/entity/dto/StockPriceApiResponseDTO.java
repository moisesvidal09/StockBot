package com.company.stockchecker.entity.dto;

import com.company.stockchecker.util.StockUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collections;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class StockPriceApiResponseDTO {

    @JsonProperty("Time Series (Daily)")
    private Map<String, StockDTO> stockByDay;

    public SortedMap<String, StockDTO> getStockByDaySorted() {
        return Collections.unmodifiableSortedMap(new TreeMap<>(StockUtil.sortMapByDateKey(this.stockByDay)));
    }
}
