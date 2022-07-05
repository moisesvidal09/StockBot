package com.company.stockchecker.service;

import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.dto.StockDTO;

import java.util.List;
import java.util.Set;

public interface IStockService extends CrudService<Stock> {

    List<StockDTO> getStocksNews(List<String> stocks);

    Set<String> findAllDistinctCode();

}
