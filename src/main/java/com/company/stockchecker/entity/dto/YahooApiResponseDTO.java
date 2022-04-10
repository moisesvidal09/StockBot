package com.company.stockchecker.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class YahooApiResponseDTO {

    @JsonProperty("quoteResponse")
    private StockApiDTO stockApiDTO;

}
