package com.company.stockchecker.service;
import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.dto.StockDTO;
import com.company.stockchecker.repository.StockRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
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

      final Stock stock = Stock.builder()
              .id(this.randomLong())
              .code("BBAS3")
              .build();

      final Stock stock2 = Stock.builder()
              .id(this.randomLong())
              .code("MGLU3")
              .build();


      when(this.stockRepository.findAll()).thenReturn(Arrays.asList(stock, stock2));
   }

   @Test
   public void shouldSaveStock() {
      when(this.stockRepository.save(any(Stock.class)))
              .thenReturn(Stock.builder()
                                 .id(randomLong())
                                 .code("BBAS3")
                                 .build());

      final Stock stock = Stock.builder()
              .code("BBAS3")
              .build();

      final Long id = this.stockService.create(stock);

      verify(this.stockRepository, times(1)).save(any(Stock.class));

      ArgumentCaptor<Stock> captor = ArgumentCaptor.forClass(Stock.class);
      verify(this.stockRepository).save(captor.capture());
      Assert.assertTrue(captor.getValue().getCode().equalsIgnoreCase(stock.getCode()));

      then(id)
          .as("Checking that Stock ID is set when stored.")
                  .isNotNull();
   }

   @Test
   public void shouldThrownEntityNotFoundWhenStockNotExist(){

      Long stockId = this.randomLong();

      assertThatThrownBy(() -> this.stockService.getById(stockId))
              .isInstanceOf(EntityNotFoundException.class)
              .hasMessageContaining("Was not possible to find stock with id = "+stockId);
   }

   @Test
   public void shouldRetrieveAllStocks(){
      Assert.assertNotNull(this.stockService.getAll());
      verify(this.stockRepository, times(1)).findAll();
   }

   @Test
   public void shouldThrownEntityNotFoundExceptionWhenDeleteStockNonexistent(){
      final Long id = this.randomLong();

      assertThatThrownBy(() -> this.stockService.delete(id))
              .isInstanceOf(EntityNotFoundException.class)
              .hasMessageContaining("Was not possible to find stock with id = "+id);

      verify(this.stockRepository, times(1)).existsById(any(Long.class));
   }

   @Test
   public void shouldReturnAnEmptyList(){

      List<StockDTO> stockDTOS = this.stockService.getStocksNews(null);

      Assert.assertTrue(stockDTOS.isEmpty());
   }

   @Test
   public void shouldReturnAnEmptyListWhenArgumentIsEmpty(){

      List<StockDTO> stockDTOS = this.stockService.getStocksNews(new ArrayList<>());

      Assert.assertTrue(stockDTOS.isEmpty());
   }

   private long randomLong() {
      return ThreadLocalRandom.current().nextLong(1000L);
   }

}
