package com.fantasmagorica.spring6reactiveexamples.controller;

import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import com.fantasmagorica.spring6reactiveexamples.mapper.BeerMapper;
import com.fantasmagorica.spring6reactiveexamples.model.BeerDTO;
import com.fantasmagorica.spring6reactiveexamples.repository.BeerRepository;
import com.fantasmagorica.spring6reactiveexamples.service.BeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v2/beer";
    public static final String BEER_PATH_SLASH = "/api/v2/beer/";
    public static final String BEER_PATH_ID = BEER_PATH_SLASH + "{beerId}";
    public static final String BEER_PATH_ID_SLASH = BEER_PATH_SLASH + "{beerId}/";

    @Autowired
    BeerService beerService;

    @Autowired
    BeerMapper beerMapper;

    @GetMapping(path = {BEER_PATH, BEER_PATH_SLASH})
    Flux<BeerDTO> listBeers(){
        return beerService.listBeers();
    }

    @GetMapping(path = {BEER_PATH_ID, BEER_PATH_ID_SLASH})
    Mono<BeerDTO> getBeerById(@PathVariable("beerId") Integer beerId){
        return beerService.getBeerById(beerId);
    }

    @PostMapping(path = {BEER_PATH, BEER_PATH_SLASH})
    Mono<ResponseEntity<Void>> createNewBeer(@RequestBody @Validated BeerDTO beerDTO){
        return beerService.saveNewBeer(beerDTO)
                .map(savedDto -> ResponseEntity.created(UriComponentsBuilder
                                .fromHttpUrl("http://localhost:8080/" + BEER_PATH_SLASH + savedDto.getId())
                                .build().toUri())
                        .build());

    }

    /**
    Notes on Update:
     1. Full update. (full update means creating a new instance with the existing id)
     2. Partial update (partial update means you get your record to the persistence context, by using findById for example, then update it and save it back)

     Using the same post method above can update by passing DTO with id, but looses de created date field.
     Partial update implementation using boring PUT, copying values from passed DTO to entity found on context is the way to go.
     */
    @PutMapping(path = {BEER_PATH_ID, BEER_PATH_ID_SLASH})
    Mono<ResponseEntity<Void>> updateBeer(@PathVariable("beerId") Integer beerId, @RequestBody @Validated BeerDTO beerDTO){
        return beerService.updateBeer(beerId, beerDTO)
                .map(savedDto -> ResponseEntity.ok().build());

    }

    @DeleteMapping(path = {BEER_PATH_ID, BEER_PATH_ID_SLASH})
    Mono<ResponseEntity<Void>> deleteBeer(@PathVariable("beerId") Integer beerId){
        return beerService.deleteBeer(beerId)
                .map(savedDto -> ResponseEntity.noContent().build());
    }
}
