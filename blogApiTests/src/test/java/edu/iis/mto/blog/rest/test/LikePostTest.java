package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class LikePostTest extends FunctionalTests {

    private static final String LIKE_API = "/blog/user/{userId}/like/{postId}";

    @Test
    public void likeByUnconfirmedUserReturnsError() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(LIKE_API, 2, 1);
    }

    @Test
    public void likeByConfirmedUserReturnsOk() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(LIKE_API, 3, 1);
    }

    @Test
    public void cannotLikeOwnPost() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_FORBIDDEN)
                   .when()
                   .post(LIKE_API, 1, 1);
    }

    @Test
    public void secondLikeDoesntMatter() {
        RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_OK)
                   .when()
                   .post(LIKE_API, 1, 1);

      RestAssured.given()
                 .accept(ContentType.JSON)
                 .header("Content-Type", "application/json;charset=UTF-8")
                 .expect()
                 .log()
                 .all()
                 .statusCode(HttpStatus.SC_OK)
                 .when()
                 .post(LIKE_API, 3, 1);
  }

}
