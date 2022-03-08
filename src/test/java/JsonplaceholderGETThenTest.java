import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonplaceholderGETThenTest {

    private final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private final String USERS = "users";

    @Test
    public void jsonplaceholderReadAllUsers() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS)
                .then().statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        List<String> names = json.getList("name");
        Assertions.assertEquals(10, names.size());
    }

    @Test
    public void jsonplaceholderRealOneUser() {
        given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .body("name", Matchers.equalTo("Leanne Graham"))
                .body("username", Matchers.equalTo("Bret"))
                .body("email", Matchers.equalTo("Sincere@april.biz"))
                .body("address.street", Matchers.equalTo("Kulas Light"));

//        JsonPath json = response.jsonPath();
//
//        Assertions.assertEquals(200, response.statusCode());
//        Assertions.assertEquals("Leanne Graham", json.get("name"));
//        Assertions.assertEquals("Bret", json.get("username"));
//        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
//        Assertions.assertEquals("Kulas Light", json.get("address.street"));

//        System.out.println(response.asString());
    }

    @Test
    public void jsonplaceholderRealOneUser2() {
        Response response = given()
                .when()
                .get(BASE_URL + "/" + USERS + "/1")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Leanne Graham", json.get("name"));
        Assertions.assertEquals("Bret", json.get("username"));
        Assertions.assertEquals("Sincere@april.biz", json.get("email"));
        Assertions.assertEquals("Kulas Light", json.get("address.street"));

//        System.out.println(response.asString());
    }

    //PATH VARIABLE
    @Test
    public void jsonplaceholderReadOneUserWithPathVariable() {
        given()
                .pathParams("userId", 1)
                .when()
                .get(BASE_URL + "/" + USERS + "/{userId}");
    }

    //QUERY PARAMS

    @Test
    public void jsonplaceholderRedUserWithQueryParams() {
        Response response = given()
                .queryParam("username", "Bret")
                .when()
                .get(BASE_URL + "/" + USERS)
                .then()
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(200, response.statusCode());
        Assertions.assertEquals("Leanne Graham", json.getList("name").get(0));
        Assertions.assertEquals("Bret", json.getList("username").get(0));
        Assertions.assertEquals("Sincere@april.biz", json.getList("email").get(0));
        Assertions.assertEquals("Kulas Light", json.getList("address.street").get(0));
    }

}
