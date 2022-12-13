package com.company.stockchecker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity(name = "user_app")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chat_id")
    private Long chatId;

    @NotNull
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "user_stock", joinColumns = {
            @JoinColumn(name = "user_id")
    },
    inverseJoinColumns = {
            @JoinColumn(name = "stock_id")
    })
    private Set<Stock> stocks;

    public Set<String> getStocksCode(){
        return Collections.unmodifiableSet(this.stocks
                                                .stream()
                                                .map(Stock::getCode)
                                                .map(code -> code.concat(".SA"))
                                                .collect(Collectors.toSet()));
    }

}
