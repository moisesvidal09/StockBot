package com.company.stockchecker.service;

import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.dto.NewsApiResponseDTO;
import com.company.stockchecker.entity.dto.NewsDTO;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.repository.StockRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import javax.persistence.EntityNotFoundException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.stream.Collectors;

@Service
public class StockService implements CrudService<Stock> {

    private final RestService restService;
    private final StockConfig stockConfig;
    private final StockRepository stockRepository;
    private final Logger logger = LoggerFactory.getLogger(StockService.class);

    public StockService(RestService restService, StockConfig stockConfig, StockRepository stockRepository) {
        this.restService = restService;
        this.stockConfig = stockConfig;
        this.stockRepository = stockRepository;
    }

    public List<StockDTO> getStocksNews(List<String> stocks){

        if (stocks == null || stocks.isEmpty()){
            logger.warn("Stocks list empty");
            return Collections.emptyList();
        }

        String stockRequested = restService.encodeUrlUTF8(String.join(" OR ", stocks));

        String URL = stockConfig.getStockNewsUrl() + stockRequested;

       // URI += "&from=" + LocalDate.now() + "&to=" + LocalDate.now();

        NewsApiResponseDTO apiResponse = restService
                .makeRequest(URL, "X-API-KEY", stockConfig.getApiKey(), NewsApiResponseDTO.class, HttpMethod.GET);


        List<StockDTO> stockDTOS = stocks.stream()
                .map(stockCode -> StockDTO.builder().symbol(stockCode).build())
                .collect(Collectors.toList());

        BiPredicate<StockDTO, NewsDTO> containsStockCodeInDescriptionOrContent = (stock, news) ->
                news.getContent().contains(stock.getSymbol()) ||
                news.getDescription().contains(stock.getSymbol());

        stockDTOS.forEach(stock -> {

            List<NewsDTO> stockNews = apiResponse.getNews()
                                            .stream()
                                            .filter(news -> containsStockCodeInDescriptionOrContent.test(stock, news))
                                            .collect(Collectors.toList());

            stock.setNews(stockNews);
        });

        return stockDTOS;
    }

    public Set<Stock> findAllDistinctCode(){
        return stockRepository.findAllDistinctCode()
                .orElse(Collections.emptySet());
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
