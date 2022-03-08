import com.github.javafaker.Faker;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderHomeworkPostsTest {

    private String expectedBody = "est rerum tempore vitae\nsequi sint nihil reprehenderit dolor beatae ea dolores neque\nfugiat blanditiis voluptate porro vel nihil molestiae ut reiciendis\nqui aperiam non debitis possimus qui neque nisi nulla";
    private static Faker faker;
    private int fakeUserId;
    private String fakeTitle;
    private String fakeBody;

    @BeforeAll
    private static void beforeAll() {
        faker = new Faker();
    }

    @BeforeEach
    private void beforeEach() {
        fakeUserId = faker.number().numberBetween(1, 1000);
        fakeTitle = faker.lorem().sentence();
        fakeBody = faker.lorem().sentence(10);

    }

    @Test
    public void jsonplaceholderGetSinglePost() {


        System.out.println(expectedBody);
        Response response = given()
                .pathParams("userId", 2)
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/{userId}")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("qui est esse", jsonPath.get("title"));
        Assertions.assertEquals(1, jsonPath.getInt("userId"));
        Assertions.assertEquals(expectedBody, jsonPath.get("body"));
        System.out.println(expectedBody);

    }

    @Test
    public void jsonplaceholderGetAllPosts() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/posts/")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals("qui est esse", jsonPath.getList("title").get(1));
        Assertions.assertEquals(1, jsonPath.getList("userId").get(1));
        Assertions.assertEquals(expectedBody, jsonPath.getList("body").get(1));

    }

    @Test
    public void jsonplaceholderPost() {

        JSONObject post = new JSONObject();
        post.put("userId", fakeUserId);
        post.put("title", fakeTitle);
        post.put("body", fakeBody);


        Response response = given()
                .contentType("application/json")
                .body(post.toString())
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(fakeUserId, jsonPath.getInt("userId"));
        Assertions.assertEquals(fakeTitle, jsonPath.get("title"));
        Assertions.assertEquals(fakeBody, jsonPath.get("body"));

    }
    @Test
    public void jsonplaceholderPut() {

        JSONObject put = new JSONObject();
        put.put("userId", fakeUserId);
        put.put("title", fakeTitle);
        put.put("body", fakeBody);

      Response response = given()
               .contentType("application/json")
               .body(put.toString())
               .when()
               .put("https://jsonplaceholder.typicode.com/users/2")
               .then()
               .statusCode(200)
               .extract()
               .response();

      JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(fakeUserId, jsonPath.getInt("userId"));
        Assertions.assertEquals(fakeTitle, jsonPath.get("title"));
        Assertions.assertEquals(fakeBody, jsonPath.get("body"));

    }

    @Test
    public void jsonplaceholderPatch() {

        JSONObject patch = new JSONObject();
        patch.put("title", fakeTitle);

        Response response = given()
                .contentType("application/json")
                .body(patch.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/2")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
        Assertions.assertEquals(fakeTitle, jsonPath.get("title"));
    }
}


