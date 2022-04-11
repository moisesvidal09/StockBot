package com.company.stockchecker.entity;

import com.company.stockchecker.entity.enums.Currency;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Entity
public class Stock extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String code;

    private String region;

    @Column(name = "full_name")
    private String fullName;

    private Currency currency;

}
