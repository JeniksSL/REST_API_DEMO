package com.rest_api_demo.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.service.core.PageDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


import static com.rest_api_demo.util.TestParameters.*;
import static org.junit.jupiter.api.Assertions.*;

class SubstanceControllerTest {


    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;
        SubstanceDto substanceDto= generateSubstanceDto();
        RestAssured
                .given()
                .spec(getAdminSpec())
                .body(substanceDto)
                .when()
                .post("/substances");
    }


    @AfterAll
    static void tearDown() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", SUB_ID)
                .when()
                .delete("/substances/{id}");
    }


    @Test
    void save() {
        tearDown();
        SubstanceDto substanceDto= generateSubstanceDto();
        SubstanceDto saved = RestAssured
                .given()
                .spec(getAdminSpec())
                .body(substanceDto)
                .when()
                .post("/substances")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .body()
                .as(SubstanceDto.class);

        assertEquals(substanceDto,saved);
    }

    @Test
    void findById() {
        SubstanceDto substanceDto = RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", generateSubstanceDto().getId())
                .when()
                .get("/substances/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(SubstanceDto.class);

        assertEquals(substanceDto, generateSubstanceDto());
    }

    @Test
    void findAll() {
        JavaType type= TypeFactory.defaultInstance().constructParametricType(PageDto.class,SubstanceDto.class);
        PageDto<SubstanceDto> pageDto= RestAssured
                .given()
                .spec(getAdminSpec())
                .when()
                .get("/substances")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);

        assertTrue(pageDto.getTotalPages()>0);
        assertTrue(pageDto.getTotalElements()>0);
        assertFalse(pageDto.getObjects().isEmpty());
        assertTrue(pageDto.getObjects().stream().anyMatch(substanceDto -> substanceDto.equals(generateSubstanceDto())));
    }

    @Test
    void findAllByCriteria() {
        JavaType type= TypeFactory.defaultInstance().constructParametricType(PageDto.class,SubstanceDto.class);
        PageDto<SubstanceDto> pageDto= RestAssured
                .given()
                .spec(getAdminSpec())
                .body(SUB_NAME.substring(SUB_NAME.length()/2))
                .when()
                .get("/substances/by-name")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);

        assertTrue(pageDto.getTotalPages()>0);
        assertTrue(pageDto.getTotalElements()>0);
        assertFalse(pageDto.getObjects().isEmpty());
        assertTrue(pageDto.getObjects().stream().anyMatch(substanceDto -> substanceDto.equals(generateSubstanceDto())));

    }



    @Test
    void update() {
        SubstanceDto substanceDto= generateSubstanceDto();
        substanceDto.setDescription("");
        substanceDto.setColor(0);
        SubstanceDto updated = RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", substanceDto.getId())
                .body(substanceDto)
                .when()
                .put("/substances/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(SubstanceDto.class);

        assertEquals(updated,substanceDto);
        assertNotEquals(updated, generateSubstanceDto());
        tearDown();
        setUp();
    }

    @Test
    void delete() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", SUB_ID)
                .when()
                .delete("/substances/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
        setUp();
    }
}