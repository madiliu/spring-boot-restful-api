package com.cathay.assignment.coin.controller;

import com.cathay.assignment.coin.service.CoinService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;

@DataJpaTest
@SpringBootTest
@DisplayName("CoinControllerTest")
public class CoinControllerTest {

    @SpyBean
    CoinController controller;

    @MockBean
    CoinService service;

    @Test
    @DisplayName("getCoinDeskDataTest")
    void getCoinDeskDataTest(){

//        when(service.getCoinDeskData().thenReturn)
    }


}
