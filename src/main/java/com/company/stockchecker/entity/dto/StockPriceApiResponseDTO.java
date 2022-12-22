package com.company.stockchecker.entity.dto;

import com.company.stockchecker.util.StockUtil;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Collections;
import java.util.Map;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@ToString
public class StockPriceApiResponseDTO {

    @JsonProperty("Time Series (Daily)")
    private Map<String, StockDTO> stockByDay;

    public Map<String, StockDTO> getStockByDay() {

        this.stockByDay = StockUtil.sortMapByDateKey(this.stockByDay);

        return Collections.unmodifiableMap(this.stockByDay);
    }
}
