package com.fantasmagorica.spring6reactiveexamples.service;

import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import com.fantasmagorica.spring6reactiveexamples.mapper.BeerMapper;
import com.fantasmagorica.spring6reactiveexamples.model.BeerDTO;
import com.fantasmagorica.spring6reactiveexamples.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class BeerServiceImpl implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public Flux<BeerDTO> listBeers() {
        return beerRepository.findAll().map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> getBeerById(Integer id) {
        return beerRepository.findById(id).map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> saveNewBeer(BeerDTO beerDTO) {
        return beerRepository.save(beerMapper.beerDtoToBeer(beerDTO))
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<BeerDTO> updateBeer(Integer id, BeerDTO beerDTO) {

        return beerRepository.findById(id).map(beer -> {
            beer.setBeerName(beerDTO.getBeerName());
            beer.setBeerStyle(beerDTO.getBeerStyle());
            beer.setPrice(beerDTO.getPrice());
            beer.setUpc(beerDTO.getUpc());
            beer.setQuantityOnHand(beerDTO.getQuantityOnHand());
            return beer;
        }).flatMap(beerRepository::save)
                .map(beerMapper::beerToBeerDto);
    }

    @Override
    public Mono<Void> deleteBeer(Integer id) {
        return beerRepository.deleteById(id);
    }
}
