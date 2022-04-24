package com.company.stockchecker.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class NewsDTO {

    private String author;

    private SourceDTO source;

    private String title;

    private String description;

    private String url;

    private LocalDateTime publishedAt;

    private String content;

}
