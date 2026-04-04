package Basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

    public class userRegistration{

      static String authToken;
      static String userId;
      static String registeredEmail;
      static String NewAuthToken;


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

            authToken = response.jsonPath().getString("data.token");

        }

        @Test (dependsOnMethods = "adminLoginTest" )
        public void registerNewAccount() {

            String apiPath = "/APIDEV/register";
            registeredEmail = Faker.instance().internet().emailAddress();
            String baseURL ="https://www.ndosiautomation.co.za";

            String payload="{\n" +
                    "  \"firstName\": \"John\",\n" +
                    "  \"lastName\": \"Snow\",\n" +
                    "  \"email\": \"%s\",\n" +
                    "  \"password\": \"@password123\",\n" +
                    "  \"confirmPassword\": \"@password123\",\n" +
                    "  \"groupId\": \"1deae17a-c67a-4bb0-bdeb-df0fc9e2e526\"\n" +
                    "}", registeredEmail;

            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type","application/json")
                    .body(payload)
                    .log().all()
                    .post().prettyPeek();

            userId = response.jsonPath().getString("data.id");


        }
        @Test(dependsOnMethods = "registerNewAccount")
        public void ApproveUser() {

            String apiPath = "/APIDEV/admin/users/" + userId + "/status";
            String baseURL = "https://www.ndosiautomation.co.za";

            String payload = "{\n" +
                    "  \"isActive\": true\n" +
                    "}";
            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .pathParam("userId", userId)
                    .body(payload)
                    .log().all()
                    .put().prettyPeek();
        }

        @Test (dependsOnMethods = "ApproveUser")

        public void NewAdmin() {

            String apiPath = "/APIDEV/admin/users/" + userId + "/role";
            String baseURL = "https://www.ndosiautomation.co.za";

            String payload = "{{\n" +
                    "  \"role\": \"admin\"\n" +
                    "}";
            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .pathParam("userId", userId)
                    .body(payload)
                    .log().all()
                    .put().prettyPeek();
        }
        @Test(dependsOnMethods = "NewAdmin")
        public void NewadminLoginTest() {

            String apiPath = "/APIDEV/login";
            String baseURL ="https://www.ndosiautomation.co.za";

            String payload=String.format("{\n" +
                    "  \"email\": \"%s\",\n" +
                    "  \"password\": \"@password123\"\n" +
                    "}",registeredEmail);

            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type","application/json")
                    .body(payload)
                    .log().all()
                    .post().prettyPeek();

            NewAuthToken = response.jsonPath().getString("data.token");
            // Verify that the user is an admin

        }
        @Test(dependsOnMethods = "NewadminLoginTest")

        // Delete newly made admin
        public void DeleteUser() {

            String apiPath = "/APIDEV/admin/users/" + userId;
            String baseURL = "https://www.ndosiautomation.co.za";

            Response response = RestAssured.given()
                    .baseUri(baseURL)
                    .basePath(apiPath)
                    .header("content-type", "application/json")
                    .header("Authorization", "Bearer " + authToken)
                    .pathParam("userId", userId)
                    .log().all()
                    .delete().prettyPeek();
        }





    }

