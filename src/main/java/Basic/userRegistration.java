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

                ApiRequestBuilder.loginUserResponse(registeredEmail, "@password123")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .body("success", equalTo(true))
                        .body("data.user.role", equalTo("admin"));
            }


        @Test(dependsOnMethods = "NewadminLoginTest")
        public void DeleteUser () {

            ApiRequestBuilder.loginUserResponse("tester@gmail.com", "@password123");
            ApiRequestBuilder.deleteUserResponse()
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));

    }

    }





