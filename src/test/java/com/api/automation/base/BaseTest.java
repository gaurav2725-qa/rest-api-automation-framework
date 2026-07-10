package com.api.automation.base;

import com.api.automation.config.ConfigManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeSuite;

public class BaseTest {

    private static final Logger logger =
            LoggerFactory.getLogger(BaseTest.class);

    @BeforeSuite
    public void setUp() {
        logger.info("========== API TEST SUITE STARTING ==========");
        logger.info("Base URL: {}{}",
                ConfigManager.getInstance().get("base.url"),
                ConfigManager.getInstance().get("api.version"));
    }
}