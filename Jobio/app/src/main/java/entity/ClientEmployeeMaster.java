package entity;

import android.content.Context;
import android.content.Intent;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class ClientEmployeeMaster {
    private String photo, firstName, lastName;
    private int employeeId;

    public ClientEmployeeMaster() {
    }

    public class Fields {
        public static final String PHOTO = "photo";
        public static final String EMPLOYEE_ID = "employeeId";
        public static final String FIRST_NAME = "first_name";
        public static final String LAST_NAME = "last_name";
    }

    public static void clearCache(Context c) {
        MyFunction.clearPreference(c, Fields.PHOTO);
        MyFunction.clearPreference(c, Fields.EMPLOYEE_ID);
        MyFunction.clearPreference(c, Fields.FIRST_NAME);
        MyFunction.clearPreference(c, Fields.LAST_NAME);
    }

    public void saveFiledsToPreferences(Context c) {
        MyFunction.setStringPreference(c, Fields.PHOTO, this.getPhoto());
        MyFunction.setStringPreference(c, Fields.FIRST_NAME, this.getFirstName());
        MyFunction.setStringPreference(c, Fields.LAST_NAME, this.getLastName());
        MyFunction.setIntPreference(c, Fields.EMPLOYEE_ID, this.getEmployeeId());
        Intent intent = new Intent();
        intent.setAction("jobio.io.EMPLOYEE_DETAIL");
        intent.putExtra("name",getFirstName());
        c.sendBroadcast(intent);
    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setPhoto(objJSON.getString("photo"));
        this.setEmployeeId(objJSON.getString("employeeId").equals("null") ? 0 : objJSON.getInt("employeeId"));
        this.setFirstName(objJSON.getString("first_name").equals("null") ? "" : objJSON.getString("first_name"));
        this.setLastName(objJSON.getString("last_name").equals("null") ? "" : objJSON.getString("last_name"));
//        this.display();
    }

    public void display() {
        Logger.debug(".....................ClientEmployeeMaster...........................");
        Logger.debug("photo:" + photo);
        Logger.debug("employee id:" + employeeId);
        Logger.debug("first_name:" + firstName);
        Logger.debug("last name:" + lastName);
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
