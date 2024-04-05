package com.fantasmagorica.spring6reactiveexamples.repository;

import com.fantasmagorica.spring6reactiveexamples.bootstrap.BootStrapData;
import com.fantasmagorica.spring6reactiveexamples.config.DataBaseConfig;
import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@DataR2dbcTest
@Import({DataBaseConfig.class, BootStrapData.class})
class BeerRepositoryTest {

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

    Beer getTestBeer(){
        return Beer.builder()
                .beerName("TestBeer")
                .beerStyle("Pilsner")
                .price(BigDecimal.ONE)
                .quantityOnHand(8)
                .upc("123")
                .build();
    }
}