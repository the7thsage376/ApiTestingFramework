package payloadBuilder;

import org.json.simple.JSONObject;


// This is to
public class payloadBuilder {
    public static JSONObject loginuserPayload(String email, String password ){

       JSONObject loginUser = new JSONObject();
       loginUser.put("email", email);
       loginUser.put("password", password);

       return loginUser;

    }

    // Register user payload builder

    public static JSONObject registerUserPayload(String firstName, String lastName, String email, String password, String confirmPassword, String groupId){

        JSONObject registerUser = new JSONObject();

        registerUser.put("firstName",firstName);
        registerUser.put("lastName", lastName);
        registerUser.put("email", email);
        registerUser.put("password", password);
        registerUser.put("confirmPassword",confirmPassword);
        registerUser.put("groupId",groupId);

        return registerUser;
    }
}


//// update user to be admin user
//public static Response updateUserRoleResponse(String role) {
//
//    String apiPath = "/APIDEV/admin/users/"+registeredUserId+"/role";
//    return RestAssured.given()
//            .baseUri(baseURL)
//            .basePath(apiPath)
//            .header("Content-Type", "application/json")
//            .header("Authorization", "Bearer " + authToken)
//            .body(approveRolePayload(role))
//            .log().all()
//            .put()
//            .then().extract().response();
//}
//