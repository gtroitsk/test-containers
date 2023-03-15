package org.acme;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class CommonTestCases {
    @BeforeEach
    public void initDatabase() {
        given().body("{\"name\":\"Georgii\", \"birth\": \"2000-10-18\", \"status\": \"ALIVE\"}")
                .header("Accept", MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when().post("/person")
                .then().statusCode(201);

        given().body("{\"name\":\"test\", \"birth\": \"0001-01-01\", \"status\": \"ALIVE\"}")
                .header("Accept", MediaType.APPLICATION_JSON)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .when().post("/person")
                .then().statusCode(201);
    }

    @AfterEach
    void dropDatabase() {
        given().when().delete("/person/Georgii");
        given().when().delete("/person/test");
    }

    @Test
    public void personCount() {
        List<Object> list = given().when().get("/person")
                .then().statusCode(200)
                .extract().response().jsonPath().getList(".");

        assertEquals(2, list.size());
    }

    @Test
    public void personList() {
        given().when().get("/person")
                .then().statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("Georgii", "test"),
                        "birth", containsInAnyOrder("2000-10-18", "0001-01-01"),
                        "status", containsInAnyOrder("ALIVE", "ALIVE"));
    }

    @Test
    public void personGetExactUser() {
        Map<Object, Object> person = given().when().get("/person/Georgii")
                .then().statusCode(200)
                .extract().response().jsonPath().getMap(".");

        assertEquals("Georgii", person.get("name"));
        assertEquals("2000-10-18", person.get("birth"));
        assertEquals("ALIVE", person.get("status"));
    }

    @Test
    public void deletePerson() {
        // Delete person Georgii
        given().when().delete("/person/Georgii")
                .then().statusCode(200);

        // List of all person in database
        List<Object> list = given().when().get("/person")
                .then().statusCode(200)
                .extract().response().jsonPath().getList(".");

        assertEquals(1, list.size());

        // Check if only test user exists
        Map<Object, Object> person = given().when().get("/person/test")
                .then().statusCode(200)
                .extract().response().jsonPath().getMap(".");

        assertEquals("test", person.get("name"));
        assertEquals("0001-01-01", person.get("birth"));
        assertEquals("ALIVE", person.get("status"));
    }

}
