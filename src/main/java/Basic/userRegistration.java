package Basic;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

    public class userRegistration{

        @Test
        public void adminLoginTest() {

            String apiPath = "/APIDEV/login";
            String baseURL ="https://www.ndosiautomation.co.za";

            String payload="{\n" +
                    "  \"email\": \"tester@gmail.com\",\n" +
                    "  \"password\": \"@password123\"\n" +
                    "}";
            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type","application/json")
                    .body(payload)
                    .log().all()
                    .post().prettyPeek();


        }
    }

