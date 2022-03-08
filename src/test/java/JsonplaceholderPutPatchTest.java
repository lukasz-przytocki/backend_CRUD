import com.github.javafaker.Faker;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class JsonplaceholderPutPatchTest {

    private static Faker faker;
    private String name;
    private String username;
    private String emailAddress;

    @BeforeAll
    public static void beforeAll(){
        faker = new Faker();

    }

    @BeforeEach
    public void beforeEach(){
        emailAddress = faker.internet().emailAddress();
        name = faker.name().fullName();
        username = faker.name().username();
    }

    @Test
    public void jsonplaceholderUpdateUserPUTTest() {

        JSONObject geo = new JSONObject();
        geo.put("lat","-68.6102");
        geo.put("lng","-47.0653");

        JSONObject address = new JSONObject();
        address.put("street","Douglas Extension");
        address.put("suite","Suite 855");
        address.put("city","McKenziehaven");
        address.put("zipcode","59590-4157");
        address.put("geo",geo);

        JSONObject company = new JSONObject();
        company.put("name","Romaguera-Jacobson");
        company.put("catchPhrase","ace to face bifurcated interface");
        company.put("bs","e-enable strategic applications");

        JSONObject user = new JSONObject();
        user.put("name", name);
        user.put("username",username);
        user.put("email",emailAddress);
        user.put("address",address);
        user.put("phone","1-463-123-4447");
        user.put("website","ramiro.info");
        user.put("company",company);

        Response response = given()
                .contentType("application/json")
                .body(user.toString())
                .when()
                .put("https://jsonplaceholder.typicode.com/users/3")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(name, json.get("name"));
        Assertions.assertEquals(username, json.get("username"));
        Assertions.assertEquals(emailAddress, json.get("email"));
    }

    @Test
    public void jsonplaceholderUpdateUserPATCHTest() {

        JSONObject email = new JSONObject();
        email.put("email",emailAddress);

        Response response = given()
                .contentType("application/json")
                .body(email.toString())
                .when()
                .patch("https://jsonplaceholder.typicode.com/users/3")
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath json = response.jsonPath();
        Assertions.assertEquals(emailAddress, json.get("email"));
    }
}
