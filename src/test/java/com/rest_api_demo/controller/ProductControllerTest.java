package com.rest_api_demo.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.ProductCriteria;
import com.rest_api_demo.service.core.PageDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;


import java.util.Set;

import static com.rest_api_demo.util.TestParameters.*;
import static com.rest_api_demo.util.TestParameters.STATIC_USER_EMAIL;
import static org.junit.jupiter.api.Assertions.*;

class ProductControllerTest {

    private static Long productId;

    @BeforeAll
    static void setUp() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = PORT;

        UserCompact userCompact = UserCompact
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(STATIC_USER_PASSWORD)
                .confirmedPassword(STATIC_USER_PASSWORD).build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userCompact)
                .when()
                .post("/users");
        SubstanceDto substanceDto= generateSubstanceDto();
        RestAssured
                .given()
                .spec(getAdminSpec())
                .body(substanceDto)
                .when()
                .post("/substances");
        ProductDto productDto=generateProductDto(false);
        productId=RestAssured
                .given()
                .spec(getStaticUserSpec())
                .contentType(ContentType.JSON).body(productDto)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract().body().as(ProductDto.class).getId();
    }


    @AfterAll
    static void tearDown() {
        RestAssured
                .given()
                .spec(getStaticUserSpec())
                .pathParam("id", productId)
                .when()
                .delete("/products/{id}");
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("email", STATIC_USER_EMAIL)
                .when()
                .delete("/users/delete/{email}");
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", SUB_ID)
                .when()
                .delete("/substances/{id}");

    }




   @Test
    void saveUpdateAndDelete() {
        //save
        ProductDto productDto=generateProductDto(false);
        ProductDto created = RestAssured
                .given()
                .spec(getStaticUserSpec())
                .contentType(ContentType.JSON).body(productDto)
                .when()
                .post("/products")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract().body().as(ProductDto.class);
        assertNotNull(created.getId());
        productDto.setId(created.getId());
        assertEquals(productDto,created);
        //update
       created.setImage(SUB_IMG);
       created.setFullName(SUB_NAME);
       ProductDto updated = RestAssured
               .given()
               .spec(getStaticUserSpec())
               .pathParam("id",created.getId())
               .body(created)
               .when()
               .put("/products/{id}")
               .then()
               .statusCode(HttpStatus.OK.value())
               .and()
               .extract().body().as(ProductDto.class);
       assertEquals(created, updated);
        //delete
        RestAssured
                .given()
                .spec(getStaticUserSpec())
                .pathParam("id",created.getId())
                .when()
                .delete("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value());
    }

    @Test
    void findById() {
        ProductDto productDto= RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id",productId)
                .when()
                .get("/products/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(ProductDto.class);
        ProductDto initial = generateProductDto(false);
        initial.setId(productId);
        assertEquals(productDto,initial);


    }

    @Test
    void findAllByCriteria() {
        JavaType type= TypeFactory.defaultInstance().constructParametricType(PageDto.class,ProductDto.class);
        ProductCriteria productCriteria=ProductCriteria
                .builder()
                .isCommon(false)
                .substances(Set.of(SUB_ID))
                .email(STATIC_USER_EMAIL).build();
        PageDto<ProductDto> pageDto= RestAssured
                .given()
                .spec(getStaticUserSpec())
                .body(productCriteria)
                .when()
                .get("/products/by-criteria")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);
        assertTrue(pageDto.getTotalPages()>0);
        assertTrue(pageDto.getTotalElements()>0);
        assertFalse(pageDto.getObjects().isEmpty());
        ProductDto initial = generateProductDto(false);
        initial.setId(productId);
        assertTrue(pageDto.getObjects().stream().anyMatch(productDto -> productDto.equals(initial)));
    }


    @Test
    void findAll() {
        JavaType type= TypeFactory.defaultInstance().constructParametricType(PageDto.class,ProductDto.class);
        PageDto<ProductDto> pageDto= RestAssured
                .given()
                .spec(getAdminSpec())
                .when()
                .get("/products")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);
        assertTrue(pageDto.getTotalPages()>0);
        assertTrue(pageDto.getTotalElements()>0);
        assertFalse(pageDto.getObjects().isEmpty());
        ProductDto initial = generateProductDto(false);
        initial.setId(productId);
        assertTrue(pageDto.getObjects().stream().anyMatch(productDto -> productDto.equals(initial)));
    }
}