package com.fantasmagorica.spring6reactiveexamples.service;

import com.fantasmagorica.spring6reactiveexamples.model.BeerDTO;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BeerService {

    Flux<BeerDTO> listBeers();

    Mono<BeerDTO> getBeerById(Integer id);

    Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO);

    Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO);

    Mono<Void> deleteBeer(Integer id);
}
