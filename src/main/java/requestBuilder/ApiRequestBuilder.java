package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import static payloadBuilder.payloadBuilder.loginuserPayload;
import static payloadBuilder.payloadBuilder.registerUserPayload;

import static common.BaseUri.baseURL;

public class ApiRequestBuilder {

   public static String authToken;
   public static String userId;

    public static Response loginUserResponse(String email, String password) {

        String apiPath = "/APIDEV/login";

        //JSONObject loginBody = payloadBuilder.loginuserPayload("tester@gmail.com","@password123");

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("content-type", "application/json")
                .body(loginuserPayload(email, password))
                .log().all()
                .post().prettyPeek()
                .then().extract().response();

        authToken = response.jsonPath().getString("data.token");

        return response;

    }

    public static Response registerUserResponse(String firstName, String lastName, String email, String password, String confirmPassword, String groupId) {

        String apiPath = "/APIDEV/register";

        //JSONObject loginBody = payloadBuilder.loginuserPayload("tester@gmail.com","@password123");

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("content-type", "application/json")
                .body(registerUserPayload(firstName, lastName, email, password, confirmPassword, groupId))
                .log().all()
                .post().prettyPeek()
                .then().extract().response();
        userId = response.jsonPath().getString("data.id");

        return response;

    }

    public static Response approveUserResponse(String statusValue) {

        String apiPath = "/APIDEV/admin/users/{userId}/status";
        JSONObject statusBody = new JSONObject();
        statusBody.put("isActive", statusValue);

        return RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("content-type", "application/json")
                .header("Authorization", "Bearer " + authToken)
                .pathParam("userId", userId)
                .body(statusBody.toJSONString())
                .log().all()
                .put().prettyPeek()
                .then().extract().response();

        
    }





}
