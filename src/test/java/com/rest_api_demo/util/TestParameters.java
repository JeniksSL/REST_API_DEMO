package com.rest_api_demo.util;

import com.rest_api_demo.dto.ProductDto;
import com.rest_api_demo.dto.SubstanceCompact;
import com.rest_api_demo.dto.SubstanceDto;
import com.rest_api_demo.security.dto.AuthRequest;
import com.rest_api_demo.security.dto.JwtTokenWrapper;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.math.BigDecimal;
import java.util.Set;

public final class TestParameters {

    public static final String USER_EMAIL = "my1@mail.com";
    public static final String USER_PASSWORD = "confident_password?;12";
    public static final String STATIC_USER_EMAIL = "static@mail.com";
    public static final String STATIC_USER_PASSWORD = "static_password?;12";
    public static final String TEST_ADMIN_NAME_AND_PASSWORD = "testUser@Name.com";
    public static final String BASE_URI = "http://localhost";
    public static final Integer PORT = 8080;
    public static final String SUB_ID="SO2";
    public static final String SUB_NAME="Sulphur";
    public static final Integer SUB_COLOR=0xFFFD3FAF;
    public static final String SUB_DESC="Yellow";
    public static final String SUB_IMG="/images/sulphur.jpg";

    public static final String PROD_FULL_NAME="Product";
    public static final String PROD_NAME="PD";
    public static final String PROD_IMG="/images/product.jpg";



    private TestParameters(){}

    public static JwtTokenWrapper getStaticUserTokens(){
        AuthRequest request = AuthRequest
                .builder()
                .email(STATIC_USER_EMAIL)
                .password(STATIC_USER_PASSWORD)
                .build();
         return  RestAssured
                 .given()
                 .contentType(ContentType.JSON)
                 .body(request)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .body()
                .as(JwtTokenWrapper.class);
    }
    public static JwtTokenWrapper getAdminTokens(){
        AuthRequest request = AuthRequest
                .builder()
                .email(TEST_ADMIN_NAME_AND_PASSWORD)
                .password(TEST_ADMIN_NAME_AND_PASSWORD)
                .build();
        return RestAssured
                .given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/auth/login")
                .then()
                .extract()
                .response()
                .body()
                .as(JwtTokenWrapper.class);
    }

    public static RequestSpecification getAdminSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + getAdminTokens().getAccessToken())
                .build();
    }

    public static RequestSpecification getStaticUserSpec(){
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer " + getStaticUserTokens().getAccessToken())
                .build();
    }

    public static SubstanceDto generateSubstanceDto(){
       return SubstanceDto
                .builder()
                .id(SUB_ID)
                .name(SUB_NAME)
                .color(SUB_COLOR)
                .description(SUB_DESC)
                .image(SUB_IMG)
                .build();
    }
    public static ProductDto generateProductDto(boolean isCommon){
        return ProductDto
                .builder()
                .name(PROD_NAME)
                .fullName(PROD_FULL_NAME)
                .isCommon(isCommon)
                .image(PROD_IMG)
                .substanceSet(Set.of(SubstanceCompact.builder().id(SUB_ID).content(BigDecimal.TEN).build()))
                .build();
    }




}
