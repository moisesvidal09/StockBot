package com.company.stockchecker.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StockDTO {

    private String symbol;

    private List<NewsDTO> news;

    @JsonProperty("1. open")
    private BigDecimal openDayPrice;

    @JsonProperty("2. high")
    private BigDecimal highDayPrice;

    @JsonProperty("3. low")
    private BigDecimal lowDayPrice;

    @JsonProperty("4. close")
    private BigDecimal closeDayPrice;




}
