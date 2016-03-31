package entity;

import org.json.JSONException;
import org.json.JSONObject;

import utility.Logger;

/**
 * Created by SAI on 3/11/2016.
 */
public class ClientJobContact {
    private String contactType, contactName, jobTitle, contactNoType, contactNo, email;

    public ClientJobContact(String contactType, String contactName, String jobTitle, String contactNoType, String contactNo, String email) {
        this.contactType = contactType;
        this.contactName = contactName;
        this.jobTitle = jobTitle;
        this.contactNoType = contactNoType;
        this.contactNo = contactNo;
        this.email = email;
    }
    public ClientJobContact(){

    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setContactType(objJSON.getString("contact_type").equals("null") ? "" : objJSON.getString("contact_type"));
        this.setContactName(objJSON.getString("contact_name").equals("null") ? "" : objJSON.getString("contact_name"));
        this.setJobTitle(objJSON.getString("job_title").equals("null") ? "" : objJSON.getString("job_title"));
        this.setContactNoType(objJSON.getString("contact_no_type").equals("null") ? "" : objJSON.getString("contact_no_type"));
        this.setContactNo(objJSON.getString("contact_no").equals("null") ? "" : objJSON.getString("contact_no"));
        this.setEmail(objJSON.getString("email").equals("null") ? "" : objJSON.getString("email"));
       // this.display();
    }

    public void display(){
        Logger.debug("................Contact..................");
        Logger.debug("contact type:"+contactType);
        Logger.debug("contact name:"+contactName);
        Logger.debug("job title:"+jobTitle);
        Logger.debug("contact no type:"+contactNoType);
        Logger.debug("contact no:"+contactNo);
        Logger.debug("email:"+email);
    }

    public String getContactType() {
        return contactType;
    }

    public void setContactType(String contactType) {
        this.contactType = contactType;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getContactNoType() {
        return contactNoType;
    }

    public void setContactNoType(String contactNoType) {
        this.contactNoType = contactNoType;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
