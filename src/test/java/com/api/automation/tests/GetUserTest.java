package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.client.ApiClient;
import com.api.automation.models.User;
import com.api.automation.utils.SchemaValidator;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Feature("User Management")
public class GetUserTest extends BaseTest {

    @Test
    @Story("Get Single User")
    @Description("Verify GET /users/2 returns correct user data")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSingleUser() {
        Response response = ApiClient.get("/users/2");

        Assert.assertEquals(response.statusCode(), 200,
                "Status code should be 200");

        User user = response.jsonPath()
                .getObject("data", User.class);

        Assert.assertNotNull(user, "User should not be null");
        Assert.assertEquals(user.getId(), 2,
                "User ID should be 2");
        Assert.assertNotNull(user.getEmail(),
                "Email should not be null");

        logger.info("Retrieved user: {}", user);
    }

    @Test
    @Story("Get User List")
    @Description("Verify GET /users returns list of users")
    @Severity(SeverityLevel.NORMAL)
    public void testGetUserList() {
        Response response = ApiClient.get("/users?page=1");

        Assert.assertEquals(response.statusCode(), 200,
                "Status code should be 200");

        int total = response.jsonPath().getInt("total");
        Assert.assertTrue(total > 0,
                "Total users should be greater than 0");

        logger.info("Total users: {}", total);
    }

    @Test
    @Story("Get Single User")
    @Description("Verify GET /users/999 returns 404 for unknown user")
    @Severity(SeverityLevel.NORMAL)
    public void testGetNonExistentUser() {
        Response response = ApiClient.get("/users/999");

        Assert.assertEquals(response.statusCode(), 404,
                "Status code should be 404 for unknown user");
    }

    @Test
    @Story("Get Single User")
    @Description("Verify GET /users/2 response matches JSON schema")
    @Severity(SeverityLevel.CRITICAL)
    public void testGetSingleUserSchema() {
        Response response = ApiClient.get("/users/2");

        Assert.assertEquals(response.statusCode(), 200,
                "Status code should be 200");

        SchemaValidator.validate(response, "user-schema.json");

        logger.info("Schema validation passed for /users/2");
    }
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(GetUserTest.class);


}