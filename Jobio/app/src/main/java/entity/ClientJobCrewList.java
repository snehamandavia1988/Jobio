package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 3/11/2016.
 */
public class ClientJobCrewList {
    private int id, employeeId;
    private String employeeName;

    public ClientJobCrewList(int id, int employeeId, String employeeName) {
        this.id = id;
        this.employeeId = employeeId;
        this.employeeName = employeeName;
    }

    public ClientJobCrewList() {
    }

    public ArrayList<ClientJobCrewList> parseJSON(JSONObject objJSON) throws JSONException {
        ArrayList<ClientJobCrewList> arrCrewList = new ArrayList<ClientJobCrewList>();
        JSONArray jsonArray = objJSON.getJSONArray("CrewList");
        //Logger.debug("CrewList.." + jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ClientJobCrewList objE = new ClientJobCrewList();
            objE.setId(obj.getString("id").equals("null") ? 0 : obj.getInt("id"));
            objE.setEmployeeId(obj.getString("employee_id").equals("null") ? 0 : obj.getInt("employee_id"));
            objE.setEmployeeName(obj.getString("employee_name").equals("null") ? "" : obj.getString("employee_name"));
            arrCrewList.add(objE);
           // objE.display();
        }
        return arrCrewList;
    }

    public void display() {
        Logger.debug("...............Client job crew list..............");
        Logger.debug("id:" + id);
        Logger.debug("employee id:" + employeeId);
        Logger.debug("employee name:" + employeeName);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}



