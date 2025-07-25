package br.com.algaworks.algadelivery.couriermanagement.api.controller;

import br.com.algaworks.algadelivery.couriermanagement.domain.model.Courier;
import br.com.algaworks.algadelivery.couriermanagement.domain.repository.CourierRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.UUID;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CourierControllerTest {

  @LocalServerPort
  private int port;

  @Autowired
  private CourierRepository courierRepository;


  @BeforeEach
  void setUp() {
    RestAssured.port = port;
    RestAssured.basePath = "/api/v1/couriers";
  }

  @Test
  public void shouldReturn201() {
    String requestBody = "{\"name\":\"Diego\",\"phone\":\"11999999999\"}";

    RestAssured.
        given()
          .body(requestBody)
          .contentType(ContentType.JSON)
          .accept(ContentType.JSON)
        .when()
          .post()
        .then()
          .statusCode(201)
          .body("id", Matchers.notNullValue())
          .body("name", Matchers.equalTo("Diego"));
  }

  @Test
  void shouldReturn200() {
    UUID courierId = courierRepository.saveAndFlush(
        Courier.brandNew("Diego", "11999999999")
    ).getId();

    RestAssured
        .given()
          .pathParam("courierId", courierId)
          .accept(ContentType.JSON)
        .when()
          .get("/{courierId}")
        .then()
          .statusCode(200)
          .body("id", Matchers.equalTo(courierId.toString()))
          .body("name", Matchers.equalTo("Diego"));

  }

}