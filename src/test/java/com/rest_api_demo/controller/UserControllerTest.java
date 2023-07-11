package com.rest_api_demo.controller;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.rest_api_demo.domain.RoleType;
import com.rest_api_demo.dto.UserCompact;
import com.rest_api_demo.dto.UserDto;
import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;
import com.rest_api_demo.service.core.PageDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import org.springframework.http.HttpStatus;

import java.util.*;
import java.util.stream.Collectors;

import static com.rest_api_demo.util.TestParameters.*;
import static org.junit.jupiter.api.Assertions.*;


class UserControllerTest {

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
                .pathParam("email", USER_EMAIL)
                .when()
                .delete("/users/delete/{email}");
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("email", STATIC_USER_EMAIL)
                .when()
                .delete("/users/delete/{email}");

    }


    @Test
    void saveWithNoEmail() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(null)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD).build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userCompact)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void saveWithNoPassword() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(USER_EMAIL)
                .password(null)
                .confirmedPassword(null).build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userCompact)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }


    @Test
    void save() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD).build();
        UserDto userDto = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userCompact)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract()
                .body()
                .as(UserDto.class);

        assertEquals(userDto.getEmail(), USER_EMAIL);
        assertTrue(userDto.getRoles().contains(RoleType.ROLE_USER.name()));
        assertTrue(userDto.getProducts().isEmpty());
    }

    @Test
    void saveWithSameEmail() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(STATIC_USER_EMAIL)
                .confirmedPassword(STATIC_USER_EMAIL).build();
        RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(userCompact)
                .when()
                .post("/users")
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }


    @Test
    void updatePassword() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD).build();
        UserDto userDto = RestAssured
                .given()
                .spec(getStaticUserSpec())
                .body(userCompact)
                .pathParam("id",STATIC_USER_EMAIL)
                .when()
                .patch("/users/password/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(UserDto.class);

        assertEquals(userDto.getEmail(), STATIC_USER_EMAIL);
        assertTrue(userDto.getRoles().contains(RoleType.ROLE_USER.name()));

        AuthRequest request = AuthRequest
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(USER_PASSWORD)
                .build();
        JwtTokenWrapper response = RestAssured
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
                .as(JwtTokenWrapper.class);

        assertTrue(response.getAccessToken().length() != 0);
        assertTrue(response.getRefreshToken().length() != 0);
        tearDown();
        setUp();
    }

    @Test
    void updatePasswordForWrongEmail() {
        UserCompact userCompact = UserCompact
                .builder()
                .email(USER_EMAIL)
                .password(USER_PASSWORD)
                .confirmedPassword(USER_PASSWORD).build();
        RestAssured
                .given()
                .spec(getStaticUserSpec())
                .body(userCompact)
                .pathParam("id",STATIC_USER_EMAIL)
                .when()
                .put("/users/password/{id}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());

    }


    @Test
    void findByID() {
        UserDto userDto = RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", STATIC_USER_EMAIL)
                .when()
                .get("users/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(UserDto.class);

        assertEquals(userDto.getEmail(), STATIC_USER_EMAIL);
        assertTrue(userDto.getRoles().contains(RoleType.ROLE_USER.name()));
        assertTrue(userDto.getProducts().isEmpty());
    }

    @Test
    void findByWrongID() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", "1")
                .when()
                .get("users/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void findAllByCriteria() {
        JavaType type = TypeFactory.defaultInstance().constructParametricType(PageDto.class, UserDto.class);
        String likeUserEmail = STATIC_USER_EMAIL.substring(STATIC_USER_EMAIL.length() / 2);
        PageDto<UserDto> pageDto = RestAssured
                .given()
                .spec(getAdminSpec())
                .body(likeUserEmail)
                .when()
                .get("/users/by-criteria")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);

        assertTrue(pageDto.getTotalPages() > 0);
        assertTrue(pageDto.getTotalElements() > 0);
        assertFalse(pageDto.getObjects().isEmpty());
        assertTrue(pageDto.getObjects().stream().anyMatch(userDto -> userDto.getEmail().equals(STATIC_USER_EMAIL)));
    }

    @Test
    void findAllByWrongCriteria() {
        JavaType type = TypeFactory.defaultInstance().constructParametricType(PageDto.class, UserDto.class);
        PageDto<UserDto> pageDto = RestAssured
                .given()
                .spec(getAdminSpec())
                .body(STATIC_USER_EMAIL.concat(STATIC_USER_PASSWORD))
                .when()
                .get("/users/by-criteria")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(type);

        assertEquals(0, pageDto.getTotalPages());
        assertEquals(0, pageDto.getTotalElements());
    }

    @Test
    void findAllRoles(){
        Set<String> roles = new TreeSet<>(RestAssured
                .given()
                .spec(getAdminSpec())
                .when()
                .get("/users/roles")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .jsonPath().getList(".", String.class));

        assertEquals(roles, new TreeSet<>(Arrays.stream(RoleType.values()).map(Enum::name).toList()));

    }


    @Test
    void updateRoles() {
        Set<String> roles = Arrays.stream(RoleType.values()).map(Enum::name).collect(Collectors.toSet());
        UserDto userDto=UserDto.builder().email(STATIC_USER_EMAIL).roles(roles).build();
        UserDto updated = RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", STATIC_USER_EMAIL)
                .body(userDto)
                .when()
                .put("/users/update-roles/{id}")
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .as(UserDto.class);

        assertEquals(updated.getRoles(), roles);
    }

    @Test
    void updateRolesWithWrongRole() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", USER_EMAIL)
                .body(Set.of(USER_EMAIL))
                .when()
                .put("/users/update-roles/{id}")
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }



    @Test
    void delete() {
        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("email", STATIC_USER_EMAIL)
                .when()
                .delete("/users/delete/{email}")
                .then()
                .statusCode(HttpStatus.OK.value());

        RestAssured
                .given()
                .spec(getAdminSpec())
                .pathParam("id", STATIC_USER_EMAIL)
                .when()
                .get("users/{id}")
                .then()
                .statusCode(HttpStatus.NOT_FOUND.value());

        setUp();
    }


}