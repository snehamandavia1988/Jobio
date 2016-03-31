package fragments;

import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 3/27/2016.
 */
public class SpinnerData {
    String strDate;
    ArrayList<String>latLog, Jobaddress, jobName, jobTime,contactNumber;

    public SpinnerData(String strDate, ArrayList<String> latLog, ArrayList<String> jobaddress, ArrayList<String> jobName, ArrayList<String> jobTime, ArrayList<String> contactNumber) {
        this.strDate = strDate;
        this.latLog = latLog;
        Jobaddress = jobaddress;
        this.jobName = jobName;
        this.jobTime = jobTime;
        this.contactNumber = contactNumber;
    }

    public String getStrDate() {
        return strDate;
    }

    public void setStrDate(String strDate) {
        this.strDate = strDate;
    }

    public ArrayList<String> getLatLog() {
        return latLog;
    }

    public void setLatLog(ArrayList<String> latLog) {
        this.latLog = latLog;
    }

    public ArrayList<String> getJobaddress() {
        return Jobaddress;
    }

    public void setJobaddress(ArrayList<String> jobaddress) {
        Jobaddress = jobaddress;
    }

    public ArrayList<String> getJobName() {
        return jobName;
    }

    public void setJobName(ArrayList<String> jobName) {
        this.jobName = jobName;
    }

    public ArrayList<String> getJobTime() {
        return jobTime;
    }

    public void setJobTime(ArrayList<String> jobTime) {
        this.jobTime = jobTime;
    }

    public ArrayList<String> getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(ArrayList<String> contactNumber) {
        this.contactNumber = contactNumber;
    }

    @Override
    public String toString() {
        return strDate;
    }
}

