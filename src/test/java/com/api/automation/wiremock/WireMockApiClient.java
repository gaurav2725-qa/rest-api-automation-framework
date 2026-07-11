package com.api.automation.wiremock;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WireMockApiClient {

    private static final Logger logger =
            LoggerFactory.getLogger(WireMockApiClient.class);

    private static RequestSpecification requestSpec;

    static {
        buildRequestSpec();
    }

    private static void buildRequestSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri(WireMockServerManager.BASE_URL)
                .setBasePath("/api")
                .setContentType(ContentType.JSON)
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        logger.info("WireMock ApiClient initialised: {}",
                WireMockServerManager.BASE_URL);
    }

    public static Response get(String endpoint) {
        logger.info("WireMock GET {}", endpoint);
        return RestAssured.given(requestSpec)
                .when().get(endpoint)
                .then().extract().response();
    }

    public static Response post(String endpoint, Object body) {
        logger.info("WireMock POST {}", endpoint);
        return RestAssured.given(requestSpec)
                .body(body)
                .when().post(endpoint)
                .then().extract().response();
    }

    public static Response put(String endpoint, Object body) {
        logger.info("WireMock PUT {}", endpoint);
        return RestAssured.given(requestSpec)
                .body(body)
                .when().put(endpoint)
                .then().extract().response();
    }

    public static Response delete(String endpoint) {
        logger.info("WireMock DELETE {}", endpoint);
        return RestAssured.given(requestSpec)
                .when().delete(endpoint)
                .then().extract().response();
    }
}