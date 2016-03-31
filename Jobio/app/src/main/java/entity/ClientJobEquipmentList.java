package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 2/28/2016.
 */
public class ClientJobEquipmentList {

    private int id;
    private String equipment_name, equipment_status;

    public ClientJobEquipmentList(int id, String equipment_name, String equipment_status) {
        this.id = id;
        this.equipment_name = equipment_name;
        this.equipment_status = equipment_status;
    }

    public ClientJobEquipmentList() {
    }

    public ArrayList<ClientJobEquipmentList> parseJSON(JSONObject objJSON) throws JSONException {
        ArrayList<ClientJobEquipmentList> arrEquipmentItemList = new ArrayList<ClientJobEquipmentList>();
        JSONArray jsonArray = objJSON.getJSONArray("EquipmentList");
        //Logger.debug("EquipmentList.."+jsonArray.length());
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ClientJobEquipmentList objE = new ClientJobEquipmentList();
            objE.setId(obj.getString("id").equals("null") ? 0 : obj.getInt("id"));
            objE.setEquipment_name(obj.getString("equipment_name").equals("null") ? "" : obj.getString("equipment_name"));
            objE.setEquipment_status(obj.getString("equipment_status").equals("null") ? "" : obj.getString("equipment_status"));
            arrEquipmentItemList.add(objE);
            //objE.display();
        }
        return arrEquipmentItemList;
    }

    public void display() {
        Logger.debug("...............Client job equipment list..............");
        Logger.debug("id:" + id);
        Logger.debug("Equipment name:" + equipment_name);
        Logger.debug("E status:" + equipment_status);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEquipment_name() {
        return equipment_name;
    }

    public void setEquipment_name(String equipment_name) {
        this.equipment_name = equipment_name;
    }

    public String getEquipment_status() {
        return equipment_status;
    }

    public void setEquipment_status(String equipment_status) {
        this.equipment_status = equipment_status;
    }
}

