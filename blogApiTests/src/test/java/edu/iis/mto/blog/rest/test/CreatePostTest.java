package edu.iis.mto.blog.rest.test;

import org.apache.http.HttpStatus;
import org.json.JSONObject;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreatePostTest extends FunctionalTests {

    private static final String POST_API = "/blog/user/{id}/post";

    @Test
    public void postFormByConfirmedUserReturnsOk() {
      JSONObject jsonObj = new JSONObject().put("entry", "Lorem ipsum");
      RestAssured.given()
                   .accept(ContentType.JSON)
                   .header("Content-Type", "application/json;charset=UTF-8")
                   .body(jsonObj.toString())
                   .expect()
                   .log()
                   .all()
                   .statusCode(HttpStatus.SC_CREATED)
                   .when()
                   .post(POST_API, 1);
    }

}
