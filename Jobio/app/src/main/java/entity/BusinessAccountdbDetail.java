package entity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class BusinessAccountdbDetail {
    private String subDomain;

    public BusinessAccountdbDetail() {
    }

    public final class Fields {
        public static final String SUB_DOMAIM = "subDomain";
    }

    public void display() {
        Logger.debug(".....................BusinessAccountdbDetail...........................");
        Logger.debug("subDomain:" + subDomain);
    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setSubDomain(objJSON.getString("sub_domain"));
    }

    public void saveFiledsToPreferences(Context c) {
        MyFunction.setStringPreference(c, Fields.SUB_DOMAIM, getSubDomain());
    }

    public static void clearCache(Context c) {
        MyFunction.clearPreference(c, Fields.SUB_DOMAIM);
    }
    public String getSubDomain() {
        return subDomain;
    }

    public void setSubDomain(String subDomain) {
        this.subDomain = subDomain;
    }
}
