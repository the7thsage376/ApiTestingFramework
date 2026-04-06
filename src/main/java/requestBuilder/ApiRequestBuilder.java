package requestBuilder;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import payloadBuilder.payloadBuilder;

import static common.BaseUri.baseURL;

public class ApiRequestBuilder {

    public static Response loginUserResponse(String email, String password) {
        String apiPath = "/APIDEV/login";

        JSONObject loginBody = payloadBuilder.loginuserPayload("tester@gmail.com","@password123");

        Response response = RestAssured.given()
                .baseUri(baseURL)
                .basePath(apiPath)
                .header("content-type","application/json")
                .body(loginBody.toJSONString())
                .log().all()
                .post().prettyPeek();

        authToken = response.jsonPath().getString("data.token");

        return response;

        int actualStatusCode = response.getStatusCode();
        Assert.assertEquals(actualStatusCode, 200, "Status code should be 200");

    }



    }
}
