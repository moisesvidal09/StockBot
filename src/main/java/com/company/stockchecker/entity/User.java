package com.company.stockchecker.entity;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class User {

    private Long id;

    private String chatId;

    private List<Stock> stocks;

}
