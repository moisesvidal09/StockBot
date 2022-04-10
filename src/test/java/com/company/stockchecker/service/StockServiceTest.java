package com.company.stockchecker.service;

import com.company.stockchecker.entity.dto.StockDTO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class StockServiceTest {

   @Autowired
   private StockService stockService;


   @Test
   public void shouldRetrieveBBAS3SAStock(){

      List<StockDTO> stocks = stockService.getStocksFromAPI(Collections.singletonList("BBAS3.SA"));

       Assert.assertEquals("BBAS3.SA", stocks.stream().findAny().get().getSymbol());
   }


}
