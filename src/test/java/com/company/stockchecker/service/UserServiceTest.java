package com.company.stockchecker.service;

import com.company.stockchecker.entity.Stock;
import com.company.stockchecker.entity.User;
import com.company.stockchecker.repository.UserRepository;
import com.company.stockchecker.util.TestUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.ActiveProfiles;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@ActiveProfiles("test")
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final TestUtil util = new TestUtil();

    @Test
    public void shouldReturnUserByChatId(){

        Set<Stock> stocks = Stream.of(
                        Stock.builder()
                                .id(util.randomLong())
                                .code("BBASE3")
                                .build(),
                        Stock.builder()
                                .id(util.randomLong())
                                .code("MGLU3")
                                .build())
                .collect(Collectors.toSet());

        final User user = User.builder()
                .id(util.randomLong())
                .chatId(342341L)
                .stocks(stocks)
                .build();

        when(userRepository.findByChatId(Mockito.anyLong())).thenReturn(Optional.ofNullable(user));

        User userRetrieved = this.userService.getByChatId(util.randomLong());

        Assert.assertNotNull(userRetrieved);

        verify(this.userRepository, times(1)).findByChatId(anyLong());

    }

    @Test
    public void shouldThrownEntityNotFoundWhenFindByChatId(){

        final Long chatId = util.randomLong();

        assertThatThrownBy(() -> this.userService.getByChatId(chatId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("User with chatId " + chatId + " not found !");

        verify(this.userRepository, times(1)).findByChatId(anyLong());
    }
}
