package Basic;

import com.github.javafaker.Faker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import payloadBuilder.payloadBuilder;
import requestBuilder.ApiRequestBuilder;

import static common.BaseUri.baseURL;
import static org.hamcrest.CoreMatchers.equalTo;

public class userRegistration{

      static String authToken;
      static String userId;
      static String registeredEmail;
      static String NewAuthToken;


        @Test
        public void adminLoginTest() {



        @Test (dependsOnMethods = "adminLoginTest" )

        public void registerNewAccount() {

            String apiPath = "/APIDEV/register";

            registeredEmail = Faker.instance().internet().emailAddress();
                ApiRequestBuilder.registerUserResponse("John","Snow",registeredEmail,"@password123","@password123","1deae17a-c67a-4bb0-bdeb-df0fc9e2e526")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(201)
                        .body("success", equalTo(true));

        }


        @Test(dependsOnMethods = "registerNewAccount")
        public void ApproveUser() {

                String apiPath = "/APIDEV/admin/users/{userId}/status";
                ApiRequestBuilder.approveUserResponse()
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .body("success", equalTo(true));


                @Test(dependsOnMethods = "ApproveUser")

                public void NewAdmin () {

                    String apiPath = "/APIDEV/admin/users/{userId}/role";

                    String payload = "{\n" +
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

                    int actualStatusCode = response.getStatusCode();
                    Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
                }


                @Test(dependsOnMethods = "NewAdmin")
                public void NewadminLoginTest () {

                    String apiPath = "/APIDEV/login";
                    JSONObject loginBody = payloadBuilder.loginuserPayload(registeredEmail, "@password123");


                    Response response = RestAssured.given()
                            .baseUri(baseURL)
                            .basePath(apiPath)
                            .header("content-type", "application/json")
                            .body(loginBody.toJSONString())
                            .log().all()
                            .post().prettyPeek();

                    int actualStatusCode = response.getStatusCode();
                    Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");

                    // NewAuthToken = response.jsonPath().getString("data.token");
                    // Verify that the user is an admin

                }
                @Test(dependsOnMethods = "NewadminLoginTest")

                // Delete newly made admin
                public void DeleteUser () {

                    String apiPath = "/APIDEV/admin/users/{userId}";

                    Response response = RestAssured.given()
                            .baseUri(baseURL)
                            .basePath(apiPath)
                            .header("content-type", "application/json")
                            .header("Authorization", "Bearer " + authToken)
                            .pathParam("userId", userId)
                            .log().all()
                            .delete().prettyPeek();

                    int actualStatusCode = response.getStatusCode();
                    Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");
                }
            





    }

