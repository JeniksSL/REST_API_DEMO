package com.rest_api_demo.controller;

import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.security.dto.JwtRequest;
import com.rest_api_demo.security.dto.JwtResponse;
import com.rest_api_demo.security.dto.TokenType;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static com.rest_api_demo.util.TestParameters.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthenticationControllerTest {


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
    }


    @AfterAll
    static void tearDown() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("email", STATIC_USER_EMAIL)
                .when()
                .delete("/users/delete/{email}")
                .then()
                .extract()
                .response();
    }


    @Test
    void login() {
        JwtRequest request = JwtRequest
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(STATIC_USER_PASSWORD)
                .build();
        JwtResponse response = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(JwtResponse.class);

        assertTrue(response.getAccessToken().length()!=0);
        assertTrue(response.getRefreshToken().length()!=0);
        assertEquals(response.getType(), TokenType.ACCESS_REFRESH.name());
    }

    @Test
    void loginWithWrongParameters(){
        JwtRequest request = JwtRequest
                .builder()
                .email(USER_EMAIL)
                .password(STATIC_USER_PASSWORD)
                .build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @Test
    void getNewAccessToken() {
        JwtResponse jwtResponse = getStaticUserTokens();
        jwtResponse=new JwtResponse(TokenType.ACCESS.name(), jwtResponse.getAccessToken(), jwtResponse.getRefreshToken());
        JwtResponse newResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jwtResponse)
                .when()
                .post("/auth/token")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(JwtResponse.class);

        assertNotNull(newResponse.getAccessToken());
        assertNull(newResponse.getRefreshToken());
        assertEquals(newResponse.getType(), TokenType.ACCESS.name());
    }

    @Test
    void getNewRefreshToken() {
        JwtResponse jwtResponse = getStaticUserTokens();
        jwtResponse=new JwtResponse(TokenType.REFRESH.name(), jwtResponse.getAccessToken(), jwtResponse.getRefreshToken());
        JwtResponse newResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(jwtResponse)
                .when()
                .post("/auth/token")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(JwtResponse.class);

        assertNotNull(newResponse.getAccessToken());
        assertNotNull(newResponse.getRefreshToken());
        assertEquals(newResponse.getType(), TokenType.ACCESS_REFRESH.name());
    }
}