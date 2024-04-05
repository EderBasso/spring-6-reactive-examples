package com.fantasmagorica.spring6reactiveexamples.repository;

import com.fantasmagorica.spring6reactiveexamples.domain.Person;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonRepositoryImplTest {

    PersonRepository personRepository = new PersonRepositoryImpl();

    @Test
    void testMonoByIdBlock() {
        Mono<Person> personMono = personRepository.getById(1);

        Person person = personMono.block();

        System.out.println(person.toString());
    }

    @Test
    void testMonoByIdSubscriber() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testMapOperation() {
        Mono<Person> personMono = personRepository.getById(1);

        personMono.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxBlock(){
        Flux<Person> personFlux = personRepository.findAll();

        Person person = personFlux.blockFirst();

        System.out.println(person.toString());
    }

    @Test
    void testFluxSubscriber() {
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.subscribe(person -> {
            System.out.println(person.toString());
        });
    }

    @Test
    void testFluxMapOperation(){
        Flux<Person> personFlux = personRepository.findAll();

        personFlux.map(Person::getFirstName).subscribe(System.out::println);
    }

    @Test
    void testFluxToList(){
        Flux<Person> personFlux = personRepository.findAll();

        Mono<List<Person>> listMono = personFlux.collectList();

        listMono.subscribe(people -> {
            people.forEach(person -> {
                System.out.println(person.getLastName());
            });
        });
    }

    @Test
    void testFilterOnName() {
        personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Denzel"))
                .subscribe(person -> System.out.println(person.getLastName()));
    }

    @Test
    void testGetById(){
        Mono<Person> personMono = personRepository.findAll()
                .filter(person -> person.getFirstName().equals("Xuxa"))
                .next();

        personMono.subscribe(person -> System.out.println(person.getFirstName() + " " + person.getLastName()));
    }

    @Test
    void testFindPersonByIdNotFoud(){
        Flux<Person> personFlux = personRepository.findAll();

        final Integer id = 8;

        Mono<Person> personMono = personRepository.findAll()
                .filter(person -> person.getId().equals(id))
                .single();
                //.next();

        personMono.subscribe(person -> {
            System.out.println(person.toString());
        }, throwable -> {
            System.out.println("Error in the mono");
            System.out.println(throwable.toString());
        });
    }

    @Test
    void testFindById(){
        Mono<Person> personMono = personRepository.getById(2);
        assertTrue(personMono.hasElement().block());
    }

    @Test
    void testFindByIdNotFound(){
        Mono<Person> personMono = personRepository.getById(420);
        assertFalse(personMono.hasElement().block());
    }

    @Test
    void testFindByIdStepVerifier(){
        Mono<Person> personMono = personRepository.getById(2);

        StepVerifier.create(personMono).expectNextCount(1).verifyComplete();

        personMono.subscribe(System.out::println);

    }
}