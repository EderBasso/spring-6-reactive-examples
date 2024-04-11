package com.fantasmagorica.spring6reactiveexamples.controller;

import com.fantasmagorica.spring6reactiveexamples.domain.Beer;
import com.fantasmagorica.spring6reactiveexamples.model.BeerDTO;
import com.fantasmagorica.spring6reactiveexamples.repository.BeerRepositoryTest;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockOAuth2Login;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
@AutoConfigureWebTestClient
class BeerControllerTest {

    @Autowired
    WebTestClient webTestClient;

    @Test
    @Order(1)
    void testListBeers() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH_SLASH)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody().jsonPath("$.size()").isEqualTo(3);
    }

    @Test
    @Order(2)
    void testGetById() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH_ID_SLASH, 1)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().valueEquals("Content-type", "application/json")
                .expectBody(BeerDTO.class);
    }

    @Test
    @Order(3)
    void testCreateBeer() {
        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(BeerController.BEER_PATH_SLASH)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().location("http://localhost:8080/api/v2/beer/4");
    }

    @Test
    @Order(4)
    void testUpdateBeer(){
        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(BeerController.BEER_PATH_ID_SLASH, 4)
                .body(Mono.just(BeerRepositoryTest.getTestBeer()), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(999)
    void testDeleteBeer(){
        webTestClient.mutateWith(mockOAuth2Login())
                .delete().uri(BeerController.BEER_PATH_ID_SLASH, 1)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNoContent();
    }

    @Test
    @Order(10)
    void testCreateBeerBadData() {
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerName("");

        webTestClient.mutateWith(mockOAuth2Login())
                .post().uri(BeerController.BEER_PATH_SLASH)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(11)
    void testUpdateBeerBadData(){
        Beer testBeer = BeerRepositoryTest.getTestBeer();
        testBeer.setBeerStyle("");

        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(BeerController.BEER_PATH_ID_SLASH, 4)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isBadRequest();
    }

    @Test
    @Order(12)
    void testUpdateBeerNotFound(){
        Beer testBeer = BeerRepositoryTest.getTestBeer();

        webTestClient.mutateWith(mockOAuth2Login())
                .put().uri(BeerController.BEER_PATH_ID_SLASH, 99)
                .body(Mono.just(testBeer), BeerDTO.class)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(13)
    void testGetByIdNotFound() {
        webTestClient.mutateWith(mockOAuth2Login())
                .get().uri(BeerController.BEER_PATH_ID_SLASH, 69)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @Order(999)
    void testDeleteBeerNotFound(){
        webTestClient.mutateWith(mockOAuth2Login())
                .delete().uri(BeerController.BEER_PATH_ID_SLASH, 420)
                .header("Content-type", "application/json")
                .exchange()
                .expectStatus().isNotFound();
    }


}