package com.company.stockchecker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "chat_id")
    private Long chatId;

    @NotNull
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Stock> stocks;

}
