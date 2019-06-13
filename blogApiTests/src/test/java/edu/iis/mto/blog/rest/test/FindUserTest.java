package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;

public class FindUserTest extends FunctionalTests {

    private static final String USER_API = "/blog/user/find";

    @Test
    public void getShouldReturnUsers() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .param("searchString", "Alice")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size", is(1))
                   .when()
                   .get(USER_API);
    }

    @Test
    public void getShouldNotIncludeRemovedUsers() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .param("searchString", "Ben")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("size", is(0))
                   .when()
                   .get(USER_API);
    }

}
