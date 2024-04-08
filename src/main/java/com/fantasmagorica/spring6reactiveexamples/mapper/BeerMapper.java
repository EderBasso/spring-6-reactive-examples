package com.fantasmagorica.spring6reactiveexamples.mapper;

import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import com.fantasmagorica.spring6reactiveexamples.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDtoToBeer(BeerDTO dto);

    BeerDTO beerToBeerDto(Beer beer);
}
