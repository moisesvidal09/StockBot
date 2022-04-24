package com.company.stockchecker.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String author;

    @NotNull
    private String source;

    @NotNull
    private String title;

    @NotNull
    private String description;

    @NotNull
    private String url;

    @NotNull
    @Column(name = "published_at")
    private LocalDateTime publishedAt;


}
