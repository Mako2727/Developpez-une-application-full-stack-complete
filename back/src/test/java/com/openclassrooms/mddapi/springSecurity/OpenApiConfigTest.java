package com.openclassrooms.mddapi.springSecurity;

import static org.junit.jupiter.api.Assertions.*;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

    private OpenApiConfig openApiConfig;

    @BeforeEach
    void setUp() {
        openApiConfig = new OpenApiConfig();
    }

    @Test
    void testCustomOpenAPI_notNull() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();
        assertNotNull(openAPI, "Le bean OpenAPI ne doit pas être null");
    }

    @Test
    void testCustomOpenAPI_containsSecurityScheme() {
        OpenAPI openAPI = openApiConfig.customOpenAPI();

        SecurityScheme securityScheme = openAPI.getComponents().getSecuritySchemes().get("bearerAuth");
        assertNotNull(securityScheme, "Le SecurityScheme 'bearerAuth' doit exister");

        assertEquals("Authorization", securityScheme.getName(), "Le nom du header doit être 'Authorization'");
        assertEquals(SecurityScheme.Type.HTTP, securityScheme.getType(), "Le type doit être HTTP");
        assertEquals("bearer", securityScheme.getScheme(), "Le scheme doit être 'bearer'");
        assertEquals("JWT", securityScheme.getBearerFormat(), "Le bearerFormat doit être 'JWT'");
    }
}
