package parser;

import org.json.JSONException;
import org.json.JSONObject;

import entity.ClientLoginUser;

public class parsQRCodeAndLoginDetail {
    public static ClientLoginUser parseLogin(String JSONString) throws JSONException {
       // Logger.debug(JSONString);
        ClientLoginUser objLoginUSer = new ClientLoginUser();
        JSONObject objJSON = new JSONObject(JSONString);
        objLoginUSer.setToken(objJSON.getString("token"));
        objLoginUSer.setTokenId(objJSON.getInt("token_id"));
        return objLoginUSer;
    }

    public static String parseQRCodeResult(String JSONString)throws JSONException{
        JSONObject objJSON = new JSONObject(JSONString);
        return objJSON.getString("account_logo");
    }
}
