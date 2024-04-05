package com.fantasmagorica.spring6reactiveexamples.repository;

import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BeerRepository extends ReactiveCrudRepository<Beer, Integer> {
}
