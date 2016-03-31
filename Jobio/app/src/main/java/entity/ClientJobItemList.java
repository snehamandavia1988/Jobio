package entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 2/28/2016.
 */
public class ClientJobItemList {
    private int id, jobId, refId;
    private String refType, qty, price, isPickListItem;
    private boolean status;

    public ClientJobItemList(int id, int jobId, int refId, String refType, String qty, String price, String isPickListItem, boolean status) {
        this.id = id;
        this.jobId = jobId;
        this.refId = refId;
        this.refType = refType;
        this.qty = qty;
        this.price = price;
        this.isPickListItem = isPickListItem;
        this.status = status;
    }

    public ClientJobItemList() {
    }

    public ArrayList<ClientJobItemList> parseJSON(JSONObject objJSON) throws JSONException {
        ArrayList<ClientJobItemList> arrClientJobItemList = new ArrayList<ClientJobItemList>();
        JSONArray jsonArray = objJSON.getJSONArray("ItemList");
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            ClientJobItemList objClientJobItemList = new ClientJobItemList();
            objClientJobItemList.setId(obj.getString("id").equals("null") ? 0 : obj.getInt("id"));
            objClientJobItemList.setJobId(obj.getString("job_id").equals("null") ? 0 : obj.getInt("job_id"));
            objClientJobItemList.setRefId(obj.getString("ref_id").equals("null") ? 0 : obj.getInt("ref_id"));
            objClientJobItemList.setRefType(obj.getString("ref_type").equals("null") ? "" : obj.getString("ref_type"));
            objClientJobItemList.setQty(obj.getString("qty").equals("null") ? "" : obj.getString("qty"));
            objClientJobItemList.setPrice(obj.getString("price").equals("null") ? "" : obj.getString("price"));
            objClientJobItemList.setIsPickListItem(obj.getString("is_pick_list_item").equals("null") ? "" : obj.getString("is_pick_list_item"));
            if (obj.getString("status").equals("null")) {
                objClientJobItemList.setStatus(false);
            } else if (obj.getString("status").equals("Y")) {
                objClientJobItemList.setStatus(true);
            } else if (obj.getString("status").equals("N")) {
                objClientJobItemList.setStatus(false);
            }
            arrClientJobItemList.add(objClientJobItemList);
            //objClientJobItemList.display();
        }
        return arrClientJobItemList;
    }

    public void display() {
        Logger.debug("...............Client job item list..............");
        Logger.debug("id:" + id);
        Logger.debug("Job id:" + jobId);
        Logger.debug("Ref id:" + refId);
        Logger.debug("Ref type:" + refType);
        Logger.debug("qty:" + qty);
        Logger.debug("Price:" + price);
        Logger.debug("is_pick_list_item:" + isPickListItem);
        Logger.debug("status:" + status);
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIsPickListItem() {
        return isPickListItem;
    }

    public void setIsPickListItem(String isPickListItem) {
        this.isPickListItem = isPickListItem;
    }

    public int getJobId() {
        return jobId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getRefType() {
        return refType;
    }

    public void setRefType(String refType) {
        this.refType = refType;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

