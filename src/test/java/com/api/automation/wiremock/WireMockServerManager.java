package com.api.automation.wiremock;

import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WireMockServerManager {

    private static final Logger logger =
            LoggerFactory.getLogger(WireMockServerManager.class);

    private static com.github.tomakehurst.wiremock.WireMockServer
            wireMockServer;

    public static final int PORT = 8089;
    public static final String BASE_URL =
            "http://localhost:" + PORT;

    public static void startServer() {
        wireMockServer =
                new com.github.tomakehurst.wiremock.WireMockServer(
                        WireMockConfiguration.wireMockConfig().port(PORT));
        wireMockServer.start();
        configureStubs();
        logger.info("WireMock server started on port {}", PORT);
    }

    public static void stopServer() {
        if (wireMockServer != null
                && wireMockServer.isRunning()) {
            wireMockServer.stop();
            logger.info("WireMock server stopped");
        }
    }

    public static void resetStubs() {
        if (wireMockServer != null) {
            wireMockServer.resetAll();
            configureStubs();
            logger.info("WireMock stubs reset");
        }
    }

    private static void configureStubs() {
        wireMockServer.stubFor(get(urlEqualTo("/api/users/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type",
                                "application/json")
                        .withBody("""
                                {
                                    "data": {
                                        "id": 2,
                                        "email": "janet.weaver@reqres.in",
                                        "first_name": "Janet",
                                        "last_name": "Weaver"
                                    }
                                }
                                """)));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type",
                                "application/json")
                        .withBody("""
                                {
                                    "page": 1,
                                    "per_page": 6,
                                    "total": 12,
                                    "data": [
                                        {
                                            "id": 1,
                                            "email": "george.bluth@reqres.in",
                                            "first_name": "George",
                                            "last_name": "Bluth"
                                        },
                                        {
                                            "id": 2,
                                            "email": "janet.weaver@reqres.in",
                                            "first_name": "Janet",
                                            "last_name": "Weaver"
                                        }
                                    ]
                                }
                                """)));

        wireMockServer.stubFor(
                get(urlEqualTo("/api/users/999"))
                        .willReturn(aResponse()
                                .withStatus(404)
                                .withHeader("Content-Type",
                                        "application/json")
                                .withBody("{}")));

        wireMockServer.stubFor(post(urlEqualTo("/api/users"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type",
                                "application/json")
                        .withBody("""
                                {
                                    "name": "Gaurav",
                                    "job": "Senior SDET",
                                    "id": "999",
                                    "createdAt": "2026-07-11T10:00:00.000Z"
                                }
                                """)));

        wireMockServer.stubFor(put(urlEqualTo("/api/users/2"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type",
                                "application/json")
                        .withBody("""
                                {
                                    "name": "Gaurav Updated",
                                    "job": "Principal SDET",
                                    "updatedAt": "2026-07-11T10:00:00.000Z"
                                }
                                """)));

        wireMockServer.stubFor(
                delete(urlEqualTo("/api/users/2"))
                        .willReturn(aResponse()
                                .withStatus(204)));

        logger.info("WireMock stubs configured successfully");
    }
}