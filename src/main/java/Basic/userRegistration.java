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
import static requestBuilder.ApiRequestBuilder.authToken;
import static requestBuilder.ApiRequestBuilder.userId;

public class userRegistration{


      static String registeredEmail;


        @Test
        public void adminLoginTest() {
            ApiRequestBuilder.loginUserResponse("tester@gmail.com", "@password123")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));
        }



        @Test (dependsOnMethods = "adminLoginTest" )

        public void registerNewAccount() {

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

            ApiRequestBuilder.approveUserResponse("true")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));
        }


        @Test(dependsOnMethods = "ApproveUser")

        public void NewAdminRole () {
            ApiRequestBuilder.NewAdminRoleResponse("admin")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));

                }


        @Test(dependsOnMethods = "NewAdminRole")
        public void NewadminLoginTest (){

        String apiPath = "/APIDEV/login";
        JSONObject loginBody = payloadBuilder.loginuserPayload(registeredEmail, "@password123");


        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("content-type", "application/json")
                .body(loginBody.toJSONString())
                .log().all()
                .post().prettyPeek();
        response.then().body("data.user.role", equalTo("admin"));// Verifies new account is admin

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");

    }



        @Test(dependsOnMethods = "NewadminLoginTest")
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
