package com.company.stockchecker.entity.dto;

import com.company.stockchecker.entity.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class StockDTO {

    private String symbol;

    private String region;

    private BigDecimal regularMarketPrice;

    private String longName;

    private Currency currency;

    private Long regularMarketTime;

}
