package entity;

import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 2/28/2016.
 */
public class ClientJobMaster {
    //from job master table
    private String jobName, description, parcelPackingAmt, labourCharge, tax, discount, totalAmount;
    private Date custPreferedDateTime, scheduledStartDateTime, scheduledEndDateTime, actualStartDateTime, actualEndDateTime;
    //from job type master table
    private String typeName;
    //from job status master
    private String statusName;
    //from location talbe


    public ClientJobMaster(String jobName, String description, String parcelPackingAmt, String labourCharge, String tax, String discount, String totalAmount, Date custPreferedDateTime, Date scheduledStartDateTime, Date scheduledEndDateTime, Date actualStartDateTime, Date actualEndDateTime, String typeName, String statusName) {
        this.jobName = jobName;
        this.description = description;
        this.parcelPackingAmt = parcelPackingAmt;
        this.labourCharge = labourCharge;
        this.tax = tax;
        this.discount = discount;
        this.totalAmount = totalAmount;
        this.custPreferedDateTime = custPreferedDateTime;
        this.scheduledStartDateTime = scheduledStartDateTime;
        this.scheduledEndDateTime = scheduledEndDateTime;
        this.actualStartDateTime = actualStartDateTime;
        this.actualEndDateTime = actualEndDateTime;
        this.typeName = typeName;
        this.statusName = statusName;
    }

    public ClientJobMaster() {
    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setJobName(objJSON.getString("job_name").equals("null") ? "" : objJSON.getString("job_name"));
        this.setDescription(objJSON.getString("description").equals("null") ? "" : objJSON.getString("description"));
        this.setParcelPackingAmt(objJSON.getString("parcel_packing_amt").equals("null") ? "0" : objJSON.getString("parcel_packing_amt"));
        this.setLabourCharge(objJSON.getString("labour_charge").equals("null") ? "0" : objJSON.getString("labour_charge"));
        this.setTax(objJSON.getString("tax").equals("null") ? "0" : objJSON.getString("tax"));
        this.setDiscount(objJSON.getString("discount").equals("null") ? "0" : objJSON.getString("discount"));
        this.setTotalAmount(objJSON.getString("total_amt").equals("null") ? "0" : objJSON.getString("total_amt"));
        this.setCustPreferedDateTime(MyFunction.convertStringToDate(objJSON.getString("cust_prefered_date_time"), "yyyy-MM-dd HH:mm:ss"));
        this.setScheduledStartDateTime(MyFunction.convertStringToDate(objJSON.getString("scheduled_start_date_time"), "yyyy-MM-dd HH:mm:ss"));
        this.setScheduledEndDateTime(MyFunction.convertStringToDate(objJSON.getString("scheduled_end_date_time"), "yyyy-MM-dd HH:mm:ss"));
        this.setActualStartDateTime(MyFunction.convertStringToDate(objJSON.getString("actual_start_date_time"), "yyyy-MM-dd HH:mm:ss"));
        this.setActualEndDateTime(MyFunction.convertStringToDate(objJSON.getString("actual_end_date_time"), "yyyy-MM-dd HH:mm:ss"));
        this.setTypeName(objJSON.getString("type_name").equals("null") ? "" : objJSON.getString("type_name"));
        this.setStatusName(objJSON.getString("status_name").equals("null") ? "" : objJSON.getString("status_name"));
        //this.display();
    }

    public void display() {
        Logger.debug("...............Client Job Master...........");
        Logger.debug("job_name:" + jobName);
        Logger.debug("description:" + description);
        Logger.debug("type name:" + typeName);
        Logger.debug("status name:" + statusName);
        Logger.debug("Parcel Packing amt:" + parcelPackingAmt);
        Logger.debug("Labour charge:" + labourCharge);
        Logger.debug("Tax:" + tax);
        Logger.debug("discount:" + discount);
        Logger.debug("Total Amt:" + totalAmount);
        Logger.debug("cust_prefered_date_time:" + custPreferedDateTime);
        Logger.debug("scheduled_start_date_time:" + scheduledStartDateTime);
        Logger.debug("scheduled_end_date_time:" + scheduledEndDateTime);
        Logger.debug("actual_start_date_time:" + actualStartDateTime);
        Logger.debug("actual_end_date_time:" + actualEndDateTime);
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParcelPackingAmt() {
        return parcelPackingAmt;
    }

    public void setParcelPackingAmt(String parcelPackingAmt) {
        this.parcelPackingAmt = parcelPackingAmt;
    }

    public String getLabourCharge() {
        return labourCharge;
    }

    public void setLabourCharge(String labourCharge) {
        this.labourCharge = labourCharge;
    }

    public String getTax() {
        return tax;
    }

    public void setTax(String tax) {
        this.tax = tax;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getCustPreferedDateTime() {
        return custPreferedDateTime;
    }

    public void setCustPreferedDateTime(Date custPreferedDateTime) {
        this.custPreferedDateTime = custPreferedDateTime;
    }

    public Date getScheduledStartDateTime() {
        return scheduledStartDateTime;
    }

    public void setScheduledStartDateTime(Date scheduledStartDateTime) {
        this.scheduledStartDateTime = scheduledStartDateTime;
    }

    public Date getScheduledEndDateTime() {
        return scheduledEndDateTime;
    }

    public void setScheduledEndDateTime(Date scheduledEndDateTime) {
        this.scheduledEndDateTime = scheduledEndDateTime;
    }

    public Date getActualStartDateTime() {
        return actualStartDateTime;
    }

    public void setActualStartDateTime(Date actualStartDateTime) {
        this.actualStartDateTime = actualStartDateTime;
    }

    public Date getActualEndDateTime() {
        return actualEndDateTime;
    }

    public void setActualEndDateTime(Date actualEndDateTime) {
        this.actualEndDateTime = actualEndDateTime;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
