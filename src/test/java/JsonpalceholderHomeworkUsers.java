import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class JsonpalceholderHomeworkUsers {


    @Test
    public void jsonplaceholderFilterEmailUsingStream() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
//        jsonPath.prettyPrint();

        List<String> emails = jsonPath.getList("email");

        long count = emails.stream()
                .filter(email -> email.endsWith(".pl"))
                .count();

        Assertions.assertEquals(0, count);
    }

    @Test
    public void jsonplaceholderFilterEmailUsingForEach() {

        Response response = given()
                .when()
                .get("https://jsonplaceholder.typicode.com/users")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath();
//        jsonPath.prettyPrint();

        List<String> emails = jsonPath.getList("email");
        long count = checkEmailAddressUsingForEachMethod(emails);

        Assertions.assertEquals(0, count);
    }

    long checkEmailAddressUsingForEachMethod(List<String> emails) {
        long counter = 0;
        for (String email : emails) {
            if (email.contains(".pl")) {
                counter++;
            }
        }
        return counter;
    }
}
