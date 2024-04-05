package com.fantasmagorica.spring6reactiveexamples.repository;

import com.fantasmagorica.spring6reactiveexamples.domain.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class PersonRepositoryImpl implements PersonRepository {

    Person michael = Person.builder()
            .id(1)
            .firstName("Michael")
            .lastName("Jordan")
            .build();

    Person denzel = Person.builder()
            .id(2)
            .firstName("Denzel")
            .lastName("Washington")
            .build();

    Person xuxa = Person.builder()
            .id(3)
            .firstName("Xuxa")
            .lastName("Meneghel")
            .build();

    Person serena = Person.builder()
            .id(4)
            .firstName("Serena")
            .lastName("Williams")
            .build();


    @Override
    public Mono<Person> getById(Integer id) {
        return findAll().filter(person -> person.getId().equals(id)).next();
    }

    @Override
    public Flux<Person> findAll() {
        return Flux.just(michael, denzel, xuxa, serena);
    }
}
