package com.fantasmagorica.spring6reactiveexamples.repository;

import com.fantasmagorica.spring6reactiveexamples.bootstrap.BootStrapData;
import com.fantasmagorica.spring6reactiveexamples.config.DataBaseConfig;
import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import({DataBaseConfig.class, BootStrapData.class})
public class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void saveNewBeer() {
        beerRepository.save(getTestBeer())
                .subscribe(beer -> {
                    System.out.println(beer.toString());
                });
        beerRepository.count().subscribe(System.out::println);
    }

    public static Beer getTestBeer(){
        return Beer.builder()
                .beerName("TestBeer")
                .beerStyle("Pilsner")
                .price(BigDecimal.ONE)
                .quantityOnHand(8)
                .upc("123")
                .build();
    }

    @Test
    void testCreateJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(getTestBeer()));
    }
}