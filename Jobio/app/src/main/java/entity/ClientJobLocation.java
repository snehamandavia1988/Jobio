package entity;

import org.json.JSONException;
import org.json.JSONObject;

import utility.Logger;

/**
 * Created by SAI on 3/11/2016.
 */
public class ClientJobLocation {
    private String refType, locationType, coordinates, house_no, street, landmark, postcode, city, state, country;

    public ClientJobLocation(String refType, String locationType, String coordinates, String house_no, String street, String landmark, String postcode, String city, String state, String country) {
        this.refType = refType;
        this.locationType = locationType;
        this.coordinates = coordinates;
        this.house_no = house_no;
        this.street = street;
        this.landmark = landmark;
        this.postcode = postcode;
        this.city = city;
        this.state = state;
        this.country = country;
    }

    public ClientJobLocation() {

    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setRefType(objJSON.getString("ref_type").equals("null") ? "" : objJSON.getString("ref_type"));
        this.setLocationType(objJSON.getString("location_type").equals("null") ? "" : objJSON.getString("location_type"));
        this.setCoordinates(objJSON.getString("cordinates").equals("null") ? "" : objJSON.getString("cordinates"));
        this.setHouse_no(objJSON.getString("house_no").equals("null") ? "" : objJSON.getString("house_no"));
        this.setStreet(objJSON.getString("street").equals("null") ? "" : objJSON.getString("street"));
        this.setLandmark(objJSON.getString("landmark").equals("null") ? "" : objJSON.getString("landmark"));
        this.setPostcode(objJSON.getString("postcode").equals("null") ? "" : objJSON.getString("postcode"));
        this.setCity(objJSON.getString("city").equals("null") ? "" : objJSON.getString("city"));
        this.setState(objJSON.getString("state").equals("null") ? "" : objJSON.getString("state"));
        this.setCountry(objJSON.getString("country").equals("null") ? "" : objJSON.getString("country"));
       // display();
    }

    public void display() {
        Logger.debug("ref tye:" + refType);
        Logger.debug("location tye:" + locationType);
        Logger.debug("cordinate:" + coordinates);
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getLocationType() {
        return locationType;
    }

    public void setLocationType(String locationType) {
        this.locationType = locationType;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
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

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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
}
