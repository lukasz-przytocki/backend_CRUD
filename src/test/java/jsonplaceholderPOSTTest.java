import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class jsonplaceholderPOSTTest {

    @Test
    public void jsonplaceholderCreateNewUser() {
        String jsonBody = "{\n" +
                "    \"name\": \"Clementine Bauch\",\n" +
                "    \"username\": \"Samantha\",\n" +
                "    \"email\": \"Nathan@yesenia.net\",\n" +
                "    \"address\": {\n" +
                "        \"street\": \"Douglas Extension\",\n" +
                "        \"suite\": \"Suite 847\",\n" +
                "        \"city\": \"McKenziehaven\",\n" +
                "        \"zipcode\": \"59590-4157\",\n" +
                "        \"geo\": {\n" +
                "            \"lat\": \"-68.6102\",\n" +
                "            \"lng\": \"-47.0653\"\n" +
                "        }\n" +
                "    },\n" +
                "    \"phone\": \"1-463-123-4447\",\n" +
                "    \"website\": \"ramiro.info\",\n" +
                "    \"company\": {\n" +
                "        \"name\": \"Romaguera-Jacobson\",\n" +
                "        \"catchPhrase\": \"Face to face bifurcated interface\",\n" +
                "        \"bs\": \"e-enable strategic applications\"\n" +
                "    }\n" +
                "}";

        Response response = given()
                .contentType("application/json") //w Postmanie w Headers dodawane jest automatycznie
                .body(jsonBody)
                .when()
                .post("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(201)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals("Clementine Bauch", json.get("name"));
        Assertions.assertEquals("Samantha", json.get("username"));
        Assertions.assertEquals("Nathan@yesenia.net", json.get("email"));
    }
}
