package entity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class ClientAdminUserAppsRel {
    private String isPasswordReseted, isGpsTracking, gpsRegId;

    public ClientAdminUserAppsRel() {
    }

    public class Fields{
        public static final String IS_PASSWORD_RESETED="isPasswordReseted";
        public static final String IS_GPS_TRACKING="isGpsTracking";
        public static final String GPS_REG_ID="gpsRegId";
    }

    public void display() {
        Logger.debug(".....................ClientAdminUserAppsRel...........................");
        Logger.debug("isPasswordReseted:" + isPasswordReseted);
        Logger.debug("isGpsTracking:" + isGpsTracking);
        Logger.debug("gpsRegId:" + gpsRegId);
    }
    public static void clearCache(Context c) {
        MyFunction.clearPreference(c, Fields.IS_PASSWORD_RESETED);
        MyFunction.clearPreference(c, Fields.IS_GPS_TRACKING);
        MyFunction.clearPreference(c, Fields.GPS_REG_ID);
    }
    public void saveFiledsToPreferences(Context c){
        MyFunction.setStringPreference(c, Fields.IS_PASSWORD_RESETED, this.getIsPasswordReseted());
        MyFunction.setStringPreference(c, Fields.IS_GPS_TRACKING, this.getIsGpsTracking());
        MyFunction.setStringPreference(c, Fields.GPS_REG_ID, this.getGpsRegId());
    }
    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setIsPasswordReseted(objJSON.getString("is_password_reseted"));
        this.setIsGpsTracking(objJSON.getString("is_gps_tracking"));
        this.setGpsRegId(objJSON.getString("gps_reg_id"));
//        this.display();
    }

    public String getIsPasswordReseted() {
        return isPasswordReseted;
    }

    public void setIsPasswordReseted(String isPasswordReseted) {
        this.isPasswordReseted = isPasswordReseted;
    }

    public String getIsGpsTracking() {
        return isGpsTracking;
    }

    public void setIsGpsTracking(String isGpsTracking) {
        this.isGpsTracking = isGpsTracking;
    }

    public String getGpsRegId() {
        return gpsRegId;
    }

    public void setGpsRegId(String gpsRegId) {
        this.gpsRegId = gpsRegId;
    }
}
