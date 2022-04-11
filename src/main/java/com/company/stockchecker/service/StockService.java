package com.company.stockchecker.service;

import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.dto.StockApiDTO;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.entity.dto.YahooApiResponseDTO;
import com.company.stockchecker.repository.StockRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import javax.persistence.EntityNotFoundException;
import java.util.*;

@Service
public class StockService implements CrudService<Stock> {

    private final RestService restService;
    private final StockConfig stockConfig;
    private final StockRepository stockRepository;

    public StockService(RestService restService, StockConfig stockConfig, StockRepository stockRepository) {
        this.restService = restService;
        this.stockConfig = stockConfig;
        this.stockRepository = stockRepository;
    }

    public List<StockDTO> getStocksFromAPI(List<String> stocks){

        String URI = stockConfig.getStockUrl() + String.join(",", stocks);

        Mono<YahooApiResponseDTO> apiResponse = restService.makeRequest(URI, "X-API-KEY", stockConfig.getApiKey())
                                                            .bodyToMono(YahooApiResponseDTO.class);

        StockApiDTO stockApiDTO =  Objects.requireNonNull(apiResponse.block()).getStockApiDTO();

        return stockApiDTO.getStocks();
    }

    public Set<Stock> findAllDistinctCode(){
        return stockRepository.findAllDistinctCode()
                .orElse(new HashSet<>());
    }

    @Override
    public Long create(Stock stock) {

        stock = Optional.ofNullable(stockRepository.save(stock))
                .orElseThrow(() -> new RuntimeException("Was not possible to save entity"));

        return stock.getId();
    }

    @Override
    public List<Stock> getAll() {
        return stockRepository.findAll();
    }

    @Override
    public Stock getById(Long id) {
        return Optional.ofNullable(stockRepository.getById(id))
                .orElseThrow(() -> new EntityNotFoundException("Was not possible to find stock with id = "+id));
    }

    @Override
    public Stock update(Stock stock) {
        return stockRepository.save(stock);
    }

    @Override
    public void delete(Long id) {
        if (!stockRepository.existsById(id)){
            throw new EntityNotFoundException("Was not possible to find stock with id = "+id);
        }

        stockRepository.deleteById(id);
    }
}
