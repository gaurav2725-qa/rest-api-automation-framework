package com.api.automation.utils;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;

public class SchemaValidator {

    private static final Logger logger =
            LoggerFactory.getLogger(SchemaValidator.class);

    private SchemaValidator() {}

    public static void validate(Response response,
                                String schemaFileName) {
        logger.info("Validating response against schema: {}",
                schemaFileName);

        InputStream schema = SchemaValidator.class
                .getClassLoader()
                .getResourceAsStream("schemas/" + schemaFileName);

        if (schema == null) {
            throw new RuntimeException(
                    "Schema file not found: " + schemaFileName);
        }

        response.then()
                .assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(schema));

        logger.info("Schema validation passed: {}", schemaFileName);
    }
}