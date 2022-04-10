package com.company.stockchecker.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class StockApiDTO {

    @JsonProperty("result")
    private List<StockDTO> stocks;

}
