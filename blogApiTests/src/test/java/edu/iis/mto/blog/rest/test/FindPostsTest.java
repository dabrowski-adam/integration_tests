package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static org.hamcrest.CoreMatchers.hasItems;

public class FindPostsTest extends FunctionalTests {

    private static final String POST_API = "/blog/user/{id}/post";

    @Test
    public void findShouldReturnPosts() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .get(POST_API, 1);
    }

    @Test
    public void findingPostsShouldWorkForRemovedUsers() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .get(POST_API, 2);
    }

    @Test
    public void foundPostShouldContainCorrectNumberOfLikes() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .and()
                   .body("likesCount", hasItems(0))
                   .when()
                   .get(POST_API, 1);

      RestAssured.given()
                 .accept(ContentType.JSON)
                 .header("Content-Type", "application/json;charset=UTF-8")
                 .expect()
                 .log()
                 .all()
                 .statusCode(HttpStatus.SC_OK)
                 .and()
                 .body("likesCount", hasItems(1))
                 .when()
                 .get(POST_API, 4);
    }

}
