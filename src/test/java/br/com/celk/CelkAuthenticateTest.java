package br.com.celk;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import io.quarkus.test.junit.QuarkusTest;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static io.restassured.RestAssured.*;
import static org.hamcrest.core.Is.is;

@Testcontainers
@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CelkAuthenticateTest {

    @Container
    public static final PostgreSQLContainer DATABASE = new PostgreSQLContainer<>()
            .withDatabaseName("clk_db")
            .withUsername("gostack")
            .withPassword("gostack")
            .withExposedPorts(5433)
            .withCreateContainerCmdModifier(cmd ->
                    cmd
                            .withHostName("localhost")
                            .withPortBindings(new PortBinding(Ports.Binding.bindPort(5433), new ExposedPort(5432)))
            )
            .withInitScript("test_user.sql");

    @Test
    @Order(1)
    void shouldAccessPublicWhenAnonymous() {
        get("/api/public")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    @Order(2)
    void shouldNotAccessAdminWhenAnonymous() {
        get("/api/admin")
                .then()
                .statusCode(HttpStatus.SC_UNAUTHORIZED);

    }

    @Test
    @Order(3)
    void shouldAccessAdminWhenAdminAuthenticated() {
        given()
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/api/admin")
                .then()
                .statusCode(HttpStatus.SC_OK);

    }

    @Test
    @Order(4)
    void shouldNotAccessUserWhenAdminAuthenticated() {
        given()
                .auth().preemptive().basic("admin", "admin")
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(HttpStatus.SC_FORBIDDEN);
    }

    @Test
    @Order(5)
    void shouldAccessUserAndGetIdentityWhenUserAuthenticated() {
        given()
                .auth().preemptive().basic("user", "user")
                .when()
                .get("/api/users/me")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
}
