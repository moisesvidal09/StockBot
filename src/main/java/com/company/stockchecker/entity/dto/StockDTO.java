package com.company.stockchecker.entity.dto;

import lombok.*;

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

}
