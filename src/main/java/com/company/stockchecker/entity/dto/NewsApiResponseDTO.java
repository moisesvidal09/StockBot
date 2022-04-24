package com.company.stockchecker.entity.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
public class NewsApiResponseDTO {

    @JsonProperty("articles")
    private List<NewsDTO> news;

}
