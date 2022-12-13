package com.company.stockchecker.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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

}
