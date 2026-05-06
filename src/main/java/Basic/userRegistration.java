package Basic;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import requestBuilder.ApiRequestBuilder;

import static org.hamcrest.CoreMatchers.equalTo;

public class userRegistration{


      static String registeredEmail;


        @Test
        public void adminLoginTest() {
            ApiRequestBuilder.loginUserResponse("testing007@gmail.com", "@password1234")
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));
        }



        @Test (dependsOnMethods = "adminLoginTest" )
        public void registerNewAccount() {

            registeredEmail = Faker.instance().internet().emailAddress();
                ApiRequestBuilder.registerUserResponse("John","Snow",registeredEmail,"@password1234","@password1234","1deae17a-c67a-4bb0-bdeb-df0fc9e2e526")
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

                ApiRequestBuilder.loginUserResponse(registeredEmail, "@password1234")
                        .then()
                        .log().all()
                        .assertThat()
                        .statusCode(200)
                        .body("success", equalTo(true))
                        .body("data.user.role", equalTo("admin"));
            }


        @Test(dependsOnMethods = "NewadminLoginTest")
        public void DeleteUser () {

            ApiRequestBuilder.loginUserResponse("testing007@gmail.com", "@password1234");
            ApiRequestBuilder.deleteUserResponse()
                    .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
                    .body("success", equalTo(true));

    }

    }





