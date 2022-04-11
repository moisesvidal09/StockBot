package com.company.stockchecker.entity;


import lombok.Data;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
public abstract class BaseEntity {

    @Column(name = "created_date")
    protected LocalDateTime createdDate;

    @Column(name = "updated_date")
    protected LocalDateTime updatedDate;

}
