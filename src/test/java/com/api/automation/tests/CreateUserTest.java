package com.api.automation.tests;

import com.api.automation.base.BaseTest;
import com.api.automation.client.ApiClient;
import com.api.automation.models.User;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Feature("User Management")
public class CreateUserTest extends BaseTest {

    private static final Logger logger =
            LoggerFactory.getLogger(CreateUserTest.class);

    @Test
    @Story("Create User")
    @Description("Verify POST /users creates a new user")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateUser() {
        User newUser = new User("Gaurav", "Senior SDET");

        Response response = ApiClient.post("/users", newUser);

        Assert.assertEquals(response.statusCode(), 201,
                "Status code should be 201 Created");

        User created = response.as(User.class);

        Assert.assertNotNull(created.getId(),
                "Created user should have an ID");
        Assert.assertEquals(created.getName(), "Gaurav",
                "Name should match");
        Assert.assertEquals(created.getJob(), "Senior SDET",
                "Job should match");
        Assert.assertNotNull(created.getCreatedAt(),
                "CreatedAt should not be null");

        logger.info("Created user: {}", created);
    }

    @Test
    @Story("Update User")
    @Description("Verify PUT /users/2 updates user data")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateUser() {
        User updatedUser = new User("Gaurav Updated",
                "Principal SDET");

        Response response = ApiClient.put("/users/2", updatedUser);

        Assert.assertEquals(response.statusCode(), 200,
                "Status code should be 200");

        User updated = response.as(User.class);

        Assert.assertEquals(updated.getName(), "Gaurav Updated",
                "Name should be updated");
        Assert.assertNotNull(updated.getUpdatedAt(),
                "UpdatedAt should not be null");

        logger.info("Updated user: {}", updated);
    }
}