package com.api.automation.wiremock;

import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Feature("WireMock API Mocking")
public class WireMockTest {

    private static final Logger logger =
            LoggerFactory.getLogger(WireMockTest.class);

    @BeforeClass
    public void startWireMock() {
        WireMockServerManager.startServer();
        logger.info("WireMock server ready for tests");
    }

    @AfterClass
    public void stopWireMock() {
        WireMockServerManager.stopServer();
    }

    @Test
    @Story("Mocked GET")
    @Description("Verify mocked GET /users/2 returns correct data")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetMockedUser() {
        Response response = WireMockApiClient.get("/users/2");

        Assert.assertEquals(response.statusCode(), 200);

        String email = response.jsonPath()
                .getString("data.email");
        int id = response.jsonPath().getInt("data.id");

        Assert.assertEquals(id, 2);
        Assert.assertEquals(email, "janet.weaver@reqres.in");

        logger.info("Mocked user verified — id: {}, email: {}",
                id, email);
    }

    @Test
    @Story("Mocked GET List")
    @Description("Verify mocked GET /users returns user list")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMockedUserList() {
        Response response = WireMockApiClient.get("/users");

        Assert.assertEquals(response.statusCode(), 200);

        int total = response.jsonPath().getInt("total");
        Assert.assertEquals(total, 12);

        logger.info("Mocked user list verified — total: {}", total);
    }

    @Test
    @Story("Mocked 404")
    @Description("Verify mocked 404 for non-existent user")
    @Severity(SeverityLevel.NORMAL)
    public void testGetMocked404() {
        Response response = WireMockApiClient.get("/users/999");

        Assert.assertEquals(response.statusCode(), 404);
        logger.info("Mocked 404 verified");
    }

    @Test
    @Story("Mocked POST")
    @Description("Verify mocked POST /users creates user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateMockedUser() {
        String body = """
                {
                    "name": "Gaurav",
                    "job": "Senior SDET"
                }
                """;

        Response response = WireMockApiClient.post("/users", body);

        Assert.assertEquals(response.statusCode(), 201);

        String name = response.jsonPath().getString("name");
        String id = response.jsonPath().getString("id");

        Assert.assertEquals(name, "Gaurav");
        Assert.assertNotNull(id);

        logger.info("Mocked user created — name: {}, id: {}",
                name, id);
    }

    @Test
    @Story("Mocked PUT")
    @Description("Verify mocked PUT /users/2 updates user")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateMockedUser() {
        String body = """
                {
                    "name": "Gaurav Updated",
                    "job": "Principal SDET"
                }
                """;

        Response response = WireMockApiClient
                .put("/users/2", body);

        Assert.assertEquals(response.statusCode(), 200);

        String name = response.jsonPath().getString("name");
        Assert.assertEquals(name, "Gaurav Updated");

        logger.info("Mocked user updated: {}", name);
    }

    @Test
    @Story("Mocked DELETE")
    @Description("Verify mocked DELETE /users/2 returns 204")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteMockedUser() {
        Response response = WireMockApiClient.delete("/users/2");

        Assert.assertEquals(response.statusCode(), 204);
        logger.info("Mocked delete verified — 204 returned");
    }
}