package com.company.stockchecker.service;

import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.dto.StockApiDTO;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.entity.dto.YahooApiResponseDTO;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;

@Service
public class StockService {

    private final RestService restService;
    private final StockConfig stockConfig;

    public StockService(RestService restService, StockConfig stockConfig) {
        this.restService = restService;
        this.stockConfig = stockConfig;
    }

    public List<StockDTO> getStocksFromAPI(List<String> stocks){

        String URI = stockConfig.getStockUrl() + String.join(",", stocks);

        Mono<YahooApiResponseDTO> apiResponse = restService.makeRequest(URI, "X-API-KEY", stockConfig.getApiKey())
                                                            .bodyToMono(YahooApiResponseDTO.class);

        StockApiDTO stockApiDTO =  Objects.requireNonNull(apiResponse.block()).getStockApiDTO();

        return stockApiDTO.getStocks();
    }


}
