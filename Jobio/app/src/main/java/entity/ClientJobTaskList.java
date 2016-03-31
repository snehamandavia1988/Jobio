package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 2/28/2016.
 */
public class ClientJobTaskList {
    private int id;
    private String taskName, description, taskStatus;

    public ClientJobTaskList(int id, String taskName, String description, String taskStatus) {
        this.id = id;
        this.taskName = taskName;
        this.description = description;
        this.taskStatus = taskStatus;
    }

    public ClientJobTaskList() {

    }

    public ArrayList<ClientJobTaskList> parseJSON(JSONObject objJSON) throws JSONException {
        ArrayList<ClientJobTaskList> arrClientJobTaskList = new ArrayList<ClientJobTaskList>();
        JSONArray jsonArray = objJSON.getJSONArray("TaskList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ClientJobTaskList objClientJobTaskList = new ClientJobTaskList();
            objClientJobTaskList.setId(obj.getString("id").equals("null") ? 0 : obj.getInt("id"));
            objClientJobTaskList.setTaskName(obj.getString("task_name").equals("null") ? "" : obj.getString("task_name"));
            objClientJobTaskList.setDescription(obj.getString("description").equals("null") ? "" : obj.getString("description"));
            objClientJobTaskList.setTaskStatus(obj.getString("task_status").equals("null") ? "" : obj.getString("task_status"));
            arrClientJobTaskList.add(objClientJobTaskList);
            //objClientJobTaskList.display();
        }
        return arrClientJobTaskList;
    }

    public void display() {
        Logger.debug("................Client Job Task List.............");
        Logger.debug("id:" + id);
        Logger.debug("Task Name:" + taskName);
        Logger.debug("Task Description:" + description);
        Logger.debug("Task status:" + taskStatus);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }
}
