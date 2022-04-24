package com.company.stockchecker.service;
import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.repository.StockRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class StockServiceTest {

   private StockService stockService;

   @Mock
   private RestService restService;

   @Mock
   private StockConfig stockConfig;

   @Mock
   private StockRepository stockRepository;


   @Before
   public void setup(){
      this.stockService = new StockService(this.restService, this.stockConfig, this.stockRepository);
   }

   @Test
   public void shouldSaveStock() {
      when(stockRepository.save(any(Stock.class)))
              .thenReturn(Stock.builder()
                                 .id(randomLong())
                                 .code("BBAS3")
                                 .build());

      final Stock stock = Stock.builder()
              .code("BBAS3")
              .build();

      final Long id = stockService.create(stock);

      verify(stockRepository, times(1)).save(any());

      ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
      verify(stockRepository).save(captor.capture());
      Assert.assertTrue(captor.getValue().getCode().equalsIgnoreCase(stock.getCode()));

      org.assertj.core.api.BDDAssertions.then(id)
                      .as("Checking that Stock ID is set when stored.")
                              .isNotNull();
   }

   @Test
   public void shouldThrownEntityNotFoundWhenStockNotExist(){

      Long stockId = new Random().nextLong();

      assertThatThrownBy(() -> this.stockService.getById(stockId))
              .isInstanceOf(EntityNotFoundException.class)
              .hasMessageContaining("Was not possible to find stock with id = "+stockId);
   }

   private long randomLong() {
      return ThreadLocalRandom.current().nextLong(1000L);
   }

}
