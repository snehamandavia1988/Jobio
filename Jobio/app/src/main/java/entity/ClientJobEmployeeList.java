package entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 2/28/2016.
 */
public class ClientJobEmployeeList {
    private int id, job_id, teamId, employeeId;
    private String employeeName, isPresent;
    private Date jobStartDatetime, jobEndDatetime;

    public ClientJobEmployeeList(int id, int job_id, int teamId, int employeeId, String employeeName, String isPresent, Date jobStartDatetime, Date jobEndDatetime) {
        this.id = id;
        this.job_id = job_id;
        this.teamId = teamId;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.isPresent = isPresent;
        this.jobStartDatetime = jobStartDatetime;
        this.jobEndDatetime = jobEndDatetime;
    }

    public ClientJobEmployeeList() {
    }

    public void parseJSON(JSONObject objJSON) throws JSONException {
        this.setId(objJSON.getString("id").equals("null") ? 0 : objJSON.getInt("id"));
        this.setJob_id(objJSON.getString("job_id").equals("null") ? 0 : objJSON.getInt("job_id"));
        this.setTeamId(objJSON.getString("team_id").equals("null") ? 0 : objJSON.getInt("team_id"));
        this.setEmployeeId(objJSON.getString("employee_id").equals("null") ? 0 : objJSON.getInt("employee_id"));
        this.setEmployeeName(objJSON.getString("employee_name"));
        this.setIsPresent(objJSON.getString("is_present"));
        this.setJobStartDatetime(MyFunction.convertStringToDate(objJSON.getString("job_start_datetime"), "yyyy-MM-dd HH:mm:ss"));
        this.setJobEndDatetime(MyFunction.convertStringToDate(objJSON.getString("job_end_datetime"), "yyyy-MM-dd HH:mm:ss"));
        //this.display();
    }

    public void display() {
        Logger.debug("................ClientJobEmployeeList................");
        Logger.debug("Id:" + id);
        Logger.debug("Job Id:" + job_id);
        Logger.debug("Team Id:" + teamId);
        Logger.debug("Employee ID:" + employeeId);
        Logger.debug("Employee Name:" + employeeName);
        Logger.debug("is present:" + isPresent);
        Logger.debug("job_start_datetime:" + jobStartDatetime);
        Logger.debug("job_end_datetime:" + jobEndDatetime);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getJob_id() {
        return job_id;
    }

    public void setJob_id(int job_id) {
        this.job_id = job_id;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getIsPresent() {
        return isPresent;
    }

    public void setIsPresent(String isPresent) {
        this.isPresent = isPresent;
    }

    public Date getJobStartDatetime() {
        return jobStartDatetime;
    }

    public void setJobStartDatetime(Date jobStartDatetime) {
        this.jobStartDatetime = jobStartDatetime;
    }

    public Date getJobEndDatetime() {
        return jobEndDatetime;
    }

    public void setJobEndDatetime(Date jobEndDatetime) {
        this.jobEndDatetime = jobEndDatetime;
    }
}
