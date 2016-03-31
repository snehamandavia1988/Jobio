package parser;

import android.location.Location;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import entity.ClientJobContact;
import entity.ClientJobCrewList;
import entity.ClientJobEmployeeList;
import entity.ClientJobEquipmentList;
import entity.ClientJobItemList;
import entity.ClientJobLocation;
import entity.ClientJobMaster;
import entity.ClientJobTaskList;
import entity.Job;
import utility.Logger;


/**
 * Created by SAI on 2/29/2016.
 */
public class parsJobList {
    public static ArrayList<Job> parseJSON(String JSONString) throws Exception {
        ArrayList<Job> arrJob = new ArrayList<Job>();
        JSONArray arrJson = new JSONArray(JSONString);
        Logger.debug("Array Size:" + arrJson.length());
        for (int i = 0; i < arrJson.length(); i++) {
            JSONObject o = arrJson.getJSONObject(i);

            ClientJobEmployeeList objClientJobEmployeeList = new ClientJobEmployeeList();
            objClientJobEmployeeList.parseJSON(arrJson.getJSONObject(i));

            ClientJobMaster objClientJobMaster = new ClientJobMaster();
            objClientJobMaster.parseJSON(arrJson.getJSONObject(i));

            ClientJobLocation objLocation = new ClientJobLocation();
            objLocation.parseJSON((arrJson.getJSONObject(i)));

            ClientJobContact objContact = new ClientJobContact();
            objContact.parseJSON(arrJson.getJSONObject(i));

            ArrayList<ClientJobItemList> arrClientJobItemList = new ClientJobItemList().parseJSON(arrJson.getJSONObject(i));

            ArrayList<ClientJobTaskList> arrClientJobTaskList = new ClientJobTaskList().parseJSON(arrJson.getJSONObject(i));

            ArrayList<ClientJobEquipmentList> arrClientJobEquipmentList = new ClientJobEquipmentList().parseJSON(arrJson.getJSONObject(i));

            ArrayList<ClientJobCrewList> arrClientJobCrewList = new ClientJobCrewList().parseJSON(arrJson.getJSONObject(i));
            Logger.debug("i:" + i + " il:" + arrClientJobItemList.size() + " jl:" + arrClientJobTaskList.size() + " el:" + arrClientJobEquipmentList.size());
            Job objJob = new Job(objClientJobEmployeeList, objClientJobMaster, objLocation, objContact, arrClientJobItemList, arrClientJobTaskList, arrClientJobEquipmentList, arrClientJobCrewList);
            arrJob.add(objJob);
        }
        return arrJob;
    }
}
