package entity;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 12/14/2015.
 */
public class BusinessAccountMaster {
    int postcode;
    String account_name,accountLogo,locationType,house_no, street, landmark, cordinates, city, state, country, accountEmail;
    long accountPhone, ownerMobile;

    public BusinessAccountMaster() {
    }

    public class Fields {
        public static final String ACCOUNT_NAME = "account_name";
        public static final String ACCOUNT_LOGO = "accountLogo";
        public static final String LOCATION_TYPE = "locationType";
        public static final String HOUSE_NO = "house_no";
        public static final String STREET = "street";
        public static final String LANDMARK = "landmark";
        public static final String POST_CODE = "postcode";
        public static final String CORDINATES = "cordinates";
        public static final String CITY = "city";
        public static final String STATE = "state";
        public static final String COUNTRY = "country";
        public static final String ACCOUNT_EMAIL = "accountEmail";
        public static final String ACCOUNT_PHONE = "accountPhone";
        public static final String OWNER_MOBILE = "ownerMobile";
    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setAccount_name(objJSON.getString("account_name"));
        this.setAccountLogo(objJSON.getString("account_logo"));
        this.setLocationType(objJSON.getString("location_type"));
        this.setHouse_no(objJSON.getString("house_no"));
        this.setStreet(objJSON.getString("street"));
        this.setLandmark(objJSON.getString("landmark"));
        this.setPostcode(objJSON.getString("postcode").equals("null") ? 0 : objJSON.getInt("postcode"));
        this.setCordinates(objJSON.getString("cordinates"));
        this.setCity(objJSON.getString("city"));
        this.setState(objJSON.getString("state"));
        this.setCountry(objJSON.getString("country"));
        this.setAccountEmail(objJSON.getString("account_email"));
        this.setAccountPhone(objJSON.getString("account_phone").equals("null") ? 0 : objJSON.getInt("account_phone"));
        this.setOwnerMobile(objJSON.getString("owner_mobile").equals("null") ? 0 : objJSON.getInt("owner_mobile"));
//        this.display();
    }
    public static void clearCache(Context c) {
        MyFunction.clearPreference(c, Fields.ACCOUNT_NAME);
        MyFunction.clearPreference(c, Fields.ACCOUNT_LOGO);
        MyFunction.clearPreference(c, Fields.ACCOUNT_EMAIL);
        MyFunction.clearPreference(c, Fields.LOCATION_TYPE);
        MyFunction.clearPreference(c, Fields.HOUSE_NO);
        MyFunction.clearPreference(c, Fields.STREET);
        MyFunction.clearPreference(c, Fields.LANDMARK);
        MyFunction.clearPreference(c, Fields.POST_CODE);
        MyFunction.clearPreference(c, Fields.CORDINATES);
        MyFunction.clearPreference(c, Fields.CITY);
        MyFunction.clearPreference(c, Fields.STATE);
        MyFunction.clearPreference(c, Fields.COUNTRY);
        MyFunction.clearPreference(c, Fields.ACCOUNT_PHONE);
        MyFunction.clearPreference(c, Fields.OWNER_MOBILE);
    }
    public void saveFiledsToPreferences(Context c){
        MyFunction.setStringPreference(c, Fields.ACCOUNT_NAME, this.getAccount_name());
        MyFunction.setStringPreference(c, Fields.ACCOUNT_LOGO, this.getAccountLogo());
        MyFunction.setStringPreference(c, Fields.ACCOUNT_EMAIL, this.getAccountEmail());
        MyFunction.setStringPreference(c, Fields.LOCATION_TYPE, this.getLocationType());
        MyFunction.setStringPreference(c, Fields.HOUSE_NO, this.getHouse_no());
        MyFunction.setStringPreference(c, Fields.STREET, this.getStreet());
        MyFunction.setStringPreference(c, Fields.LANDMARK, this.getLandmark());
        MyFunction.setIntPreference(c, Fields.POST_CODE, this.getPostcode());
        MyFunction.setStringPreference(c, Fields.CORDINATES, this.getCordinates());
        MyFunction.setStringPreference(c, Fields.CITY, this.getCity());
        MyFunction.setStringPreference(c, Fields.STATE, this.getState());
        MyFunction.setStringPreference(c, Fields.COUNTRY, this.getCountry());
        MyFunction.setLongPreference(c, Fields.ACCOUNT_PHONE, this.getAccountPhone());
        MyFunction.setLongPreference(c, Fields.OWNER_MOBILE, this.getOwnerMobile());
    }

    public void display() {
        Logger.debug(".....................BusinessAccountMaster...........................");
        Logger.debug("account_name:" + account_name);
        Logger.debug("accountLogo:" + accountLogo);
        Logger.debug("accountEmail:" + accountEmail);
        Logger.debug("locationType:" + locationType);
        Logger.debug("house_no:" + house_no);
        Logger.debug("street:" + street);
        Logger.debug("landmark:" + landmark);
        Logger.debug("cordinates:" + cordinates);
        Logger.debug("city:" + city);
        Logger.debug("state:" + state);
        Logger.debug("country:" + country);
        Logger.debug("account phone:" + accountPhone);
        Logger.debug("ownerMobile:" + ownerMobile);
    }

    public String getAccount_name() {
        return account_name;
    }

    public void setAccount_name(String account_name) {
        this.account_name = account_name;
    }

    public String getAccountLogo() {
        return accountLogo;
    }

    public void setAccountLogo(String accountLogo) {
        this.accountLogo = accountLogo;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getHouse_no() {
        return house_no;
    }

    public void setHouse_no(String house_no) {
        this.house_no = house_no;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getCordinates() {
        return cordinates;
    }

    public void setCordinates(String cordinates) {
        this.cordinates = cordinates;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAccountEmail() {
        return accountEmail;
    }

    public void setAccountEmail(String accountEmail) {
        this.accountEmail = accountEmail;
    }

    public long getAccountPhone() {
        return accountPhone;
    }

    public void setAccountPhone(long accountPhone) {
        this.accountPhone = accountPhone;
    }

    public long getOwnerMobile() {
        return ownerMobile;
    }

    public void setOwnerMobile(long ownerMobile) {
        this.ownerMobile = ownerMobile;
    }

    public int getPostcode() {
        return postcode;
    }

    public void setPostcode(int postcode) {
        this.postcode = postcode;
    }
}
