package entity;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class ClientAdminUser {
    private int id;//,employeeId,themeId,userId;
    private String userName,password;//,password,date,time,timeStamp,status;

    public ClientAdminUser(){}

    public class Fields {
        public static final String USER_NAME = "userName";
        public static final String PASSWORD = "password";
        public static final String ADMINUSERID = "adminUserid";
    }
    public static void clearCache(Context c) {
        MyFunction.clearPreference(c, Fields.USER_NAME);
        MyFunction.clearPreference(c, Fields.PASSWORD);
        MyFunction.clearPreference(c, Fields.ADMINUSERID);
    }
    public void saveFiledsToPreferences(Context c){
        MyFunction.setStringPreference(c, Fields.USER_NAME, this.getUserName());
        MyFunction.setStringPreference(c, Fields.PASSWORD, this.getPassword());
        MyFunction.setIntPreference(c, Fields.ADMINUSERID, this.getId());
    }
    public void parseJSON(JSONObject objJSON) throws JSONException{
        this.setUserName(objJSON.getString("user_name"));
        //this.setPassword(objJSON.getString("password")); No need to set password value as it in encoded format
        this.setId(objJSON.getString("id").equals("null") ? 0 : objJSON.getInt("id"));
        //this.display();
    }
    public void display() {
        Logger.debug(".....................ClientAdminUser...........................");
        Logger.debug("userName:" + userName);
        Logger.debug("Password:" + password);
        Logger.debug("id:" + this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
