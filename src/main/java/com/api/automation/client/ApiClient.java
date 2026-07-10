package com.api.automation.client;

import com.api.automation.config.ConfigManager;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiClient {

    private static final Logger logger =
            LoggerFactory.getLogger(ApiClient.class);

    private static RequestSpecification requestSpec;

    static {
        buildRequestSpec();
    }

    private static void buildRequestSpec() {
        String baseUrl = ConfigManager.getInstance().get("base.url");
        String apiVersion = ConfigManager.getInstance().get("api.version");
        String apiKey = ConfigManager.getInstance().get("api.key");

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .setBasePath(apiVersion)
                .setContentType(ContentType.JSON)
                .addHeader("x-api-key", apiKey)        // ← THIS LINE WAS MISSING
                .addFilter(new AllureRestAssured())
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .build();

        logger.info("API Client initialised: {}{}", baseUrl, apiVersion);
    }
    public static Response get(String endpoint) {
        logger.info("GET {}", endpoint);
        return RestAssured
                .given(requestSpec)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response post(String endpoint, Object body) {
        logger.info("POST {}", endpoint);
        return RestAssured
                .given(requestSpec)
                .body(body)
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response put(String endpoint, Object body) {
        logger.info("PUT {}", endpoint);
        return RestAssured
                .given(requestSpec)
                .body(body)
                .when()
                .put(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response delete(String endpoint) {
        logger.info("DELETE {}", endpoint);
        return RestAssured
                .given(requestSpec)
                .when()
                .delete(endpoint)
                .then()
                .extract()
                .response();
    }

    public static Response getWithAuth(String endpoint,
                                       String token) {
        logger.info("GET {} with Bearer token", endpoint);
        return RestAssured
                .given(requestSpec)
                .header("Authorization", "Bearer " + token)
                .when()
                .get(endpoint)
                .then()
                .extract()
                .response();
    }
}