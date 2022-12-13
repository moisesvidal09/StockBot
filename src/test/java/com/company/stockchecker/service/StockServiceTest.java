package com.company.stockchecker.service;

import com.company.stockchecker.config.StockConfig;
import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.dto.*;
import com.company.stockchecker.repository.StockRepository;
import com.company.stockchecker.util.MessageUtil;
import com.company.stockchecker.util.TestUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class StockServiceTest {

   @InjectMocks
   private StockService stockService;

   @Mock
   private RestService restService;

   @Mock
   private StockConfig stockConfig;

   @Mock
   private StockRepository stockRepository;

   private final TestUtil util = new TestUtil();

   @Before
   public void setup(){

      final Stock stock = Stock.builder()
              .id(TestUtil.randomLong())
              .code("BBAS3")
              .build();

      final Stock stock2 = Stock.builder()
              .id(TestUtil.randomLong())
              .code("MGLU3")
              .build();


      when(this.stockRepository.findAll()).thenReturn(Arrays.asList(stock, stock2));
   }

   @Test
   public void shouldSaveStock() {
      when(this.stockRepository.save(any(Stock.class)))
              .thenReturn(Stock.builder()
                                 .id(util.randomLong())
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

      Long stockId = util.randomLong();

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
      final Long id = util.randomLong();

      assertThatThrownBy(() -> this.stockService.delete(id))
              .isInstanceOf(EntityNotFoundException.class)
              .hasMessageContaining("Was not possible to find stock with id = "+id);

      verify(this.stockRepository, times(1)).existsById(any(Long.class));
   }

   @Test
   public void shouldReturnAnEmptyListWhenArgumentIsNull(){

      List<StockDTO> stockDTOS = this.stockService.getStocksNews(null);

      Assert.assertTrue(stockDTOS.isEmpty());
   }

   @Test
   public void shouldReturnAnEmptyListWhenArgumentIsEmpty(){

      List<StockDTO> stockDTOS = this.stockService.getStocksNews(new ArrayList<>());

      Assert.assertTrue(stockDTOS.isEmpty());
   }

   @Test
   public void shouldReturnStockWithNews(){
      final NewsDTO newsDTO = NewsDTO.builder()
              .author("UOL")
              .source(SourceDTO.builder().name("UOL").build())
              .title("B3 BBASE3")
              .description("DESCRIPTION")
              .url("www.google.com")
              .publishedAt(LocalDateTime.now())
              .content("content")
              .build();

      final NewsDTO newsDTO2 = NewsDTO.builder()
              .author("GLOBO")
              .source(SourceDTO.builder().name("GLOBO").build())
              .title("B3 MGLU3")
              .description("DESCRIPTION")
              .url("www.globo.com")
              .publishedAt(LocalDateTime.now())
              .content("content")
              .build();

      final NewsApiResponseDTO newsApiResponseDTO = NewsApiResponseDTO.builder()
              .news(Arrays.asList(newsDTO, newsDTO2))
              .build();

      when(this.restService.makeRequest(any(), any(), any(), any(), any())).thenReturn(newsApiResponseDTO);

      List<StockDTO> stockDTOS = this.stockService.getStocksNews(Arrays.asList("MGLU33", "BBASE33"));

      Assert.assertNotNull(stockDTOS);

      verify(this.restService, times(1)).makeRequest(any(), any(), any(), any(), any());
   }

   @Test
   public void shouldGetStockPriceFromAPI(){

      String url = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=AMER3.SA&apikey=4M6P5ET2MQVIWR31";

      StockPriceApiResponseDTO stockDTO
              = restService.makeRequest(url, "Connection", "keep-alive", StockPriceApiResponseDTO.class, HttpMethod.GET);

      Assertions.assertAll("Verify return from api",
              () -> Assert.assertNotNull(stockDTO),
              () -> Assert.assertNotNull(stockDTO.getStockByDay()),
              () -> Assert.assertFalse(stockDTO.getStockByDay().isEmpty()));
   }

   @Test
   public void shouldGetStockPricesFromAPI(){

      StockDTO stockDTO1Elet3 = StockDTO.builder()
              .highDayPrice(new BigDecimal("9.46"))
              .lowDayPrice(new BigDecimal("8.95"))
              .closeDayPrice(new BigDecimal("9.07"))
              .openDayPrice(new BigDecimal("9.15"))
              .build();

      StockDTO stockDTO2Elet3 = StockDTO.builder()
              .highDayPrice(new BigDecimal("9.66"))
              .lowDayPrice(new BigDecimal("8.91"))
              .closeDayPrice(new BigDecimal("9.0"))
              .openDayPrice(new BigDecimal("9.62"))
              .build();

      Map<String,StockDTO> mapElet3 = new HashMap<>();
      mapElet3.put(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), stockDTO1Elet3);
      mapElet3.put("2022-12-05", stockDTO2Elet3);

      StockDTO stockDTO3Amer3 = StockDTO.builder()
              .highDayPrice(new BigDecimal("9.46"))
              .lowDayPrice(new BigDecimal("8.95"))
              .closeDayPrice(new BigDecimal("9.07"))
              .openDayPrice(new BigDecimal("9.15"))
              .build();

      StockDTO stockDTO4Amer3 = StockDTO.builder()
              .highDayPrice(new BigDecimal("9.66"))
              .lowDayPrice(new BigDecimal("8.91"))
              .closeDayPrice(new BigDecimal("9.0"))
              .openDayPrice(new BigDecimal("9.62"))
              .build();

      Map<String,StockDTO> mapAmer3 = new HashMap<>();
      mapAmer3.put(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")), stockDTO3Amer3);
      mapAmer3.put("2022-12-05", stockDTO4Amer3);

      StockPriceApiResponseDTO stockPriceApiResponseDTOElet3 = StockPriceApiResponseDTO.builder()
              .stockByDay(mapElet3)
              .build();

      StockPriceApiResponseDTO stockPriceApiResponseDTOAmer3 = StockPriceApiResponseDTO.builder()
              .stockByDay(mapAmer3)
              .build();

      when(stockConfig.getStockPriceUrl()).thenReturn("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=");
      when(stockConfig.getApiKey()).thenReturn("4M6P5ET2MQVIWR31");
      when(restService.makeRequest(eq("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=ELET3.SA&apikey=4M6P5ET2MQVIWR31"),
              anyString(), anyString(), any(Class.class), any(HttpMethod.class))).thenReturn(stockPriceApiResponseDTOElet3);
      when(restService.makeRequest(eq("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY_ADJUSTED&symbol=AMER3.SA&apikey=4M6P5ET2MQVIWR31"),
              anyString(), anyString(), any(Class.class), any(HttpMethod.class))).thenReturn(stockPriceApiResponseDTOAmer3);

      List<StockDTO> stocks = stockService.getStocksPrice(Stream.of("ELET3.SA", "AMER3.SA").collect(Collectors.toSet()));

      Assertions.assertAll("Asserting stocks ",
              () -> Assert.assertNotNull(stocks),
              () -> Assert.assertFalse(stocks.isEmpty()));

      System.out.println(MessageUtil.buildMessageStockPrice(stocks));
   }

}
