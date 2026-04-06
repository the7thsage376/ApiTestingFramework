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
