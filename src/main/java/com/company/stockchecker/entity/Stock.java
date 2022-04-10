package com.company.stockchecker.entity;

import com.company.stockchecker.entity.enums.Currency;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Stock {

    private String code;

    private String region;

    private BigDecimal price;

    private String fullName;

    private Currency currency;

    private Long currentTime;
}
