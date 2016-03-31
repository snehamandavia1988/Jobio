package entity;

import java.util.ArrayList;

/**
 * Created by SAI on 2/18/2016.
 */
public class Job {
    ClientJobEmployeeList objClientJobEmployeeList;
    ClientJobMaster objClientJobMaster;
    ClientJobLocation objLocation;
    ClientJobContact objContact;
    ArrayList<ClientJobItemList> arrClientJobItemList;
    ArrayList<ClientJobTaskList> arrClientJobTaskList;
    ArrayList<ClientJobEquipmentList> arrClientJobEquipmentList;
    ArrayList<ClientJobCrewList> arrClientJobCrewList;
public Job(String d){
    objClientJobMaster.setDescription(d);
}
    public Job(ClientJobEmployeeList objClientJobEmployeeList, ClientJobMaster objClientJobMaster, ClientJobLocation objLocation, ClientJobContact objContact, ArrayList<ClientJobItemList> arrClientJobItemList, ArrayList<ClientJobTaskList> arrClientJobTaskList, ArrayList<ClientJobEquipmentList> arrClientJobEquipmentList, ArrayList<ClientJobCrewList> arrClientJobCrewList) {
        this.objClientJobEmployeeList = objClientJobEmployeeList;
        this.objClientJobMaster = objClientJobMaster;
        this.objLocation = objLocation;
        this.objContact = objContact;
        this.arrClientJobItemList = arrClientJobItemList;
        this.arrClientJobTaskList = arrClientJobTaskList;
        this.arrClientJobEquipmentList = arrClientJobEquipmentList;
        this.arrClientJobCrewList = arrClientJobCrewList;
    }

    public ClientJobEmployeeList getObjClientJobEmployeeList() {
        return objClientJobEmployeeList;
    }

    public void setObjClientJobEmployeeList(ClientJobEmployeeList objClientJobEmployeeList) {
        this.objClientJobEmployeeList = objClientJobEmployeeList;
    }

    public ClientJobMaster getObjClientJobMaster() {
        return objClientJobMaster;
    }

    public void setObjClientJobMaster(ClientJobMaster objClientJobMaster) {
        this.objClientJobMaster = objClientJobMaster;
    }

    public ClientJobLocation getObjLocation() {
        return objLocation;
    }

    public void setObjLocation(ClientJobLocation objLocation) {
        this.objLocation = objLocation;
    }

    public ClientJobContact getObjContact() {
        return objContact;
    }

    public void setObjContact(ClientJobContact objContact) {
        this.objContact = objContact;
    }

    public ArrayList<ClientJobItemList> getArrClientJobItemList() {
        return arrClientJobItemList;
    }

    public void setArrClientJobItemList(ArrayList<ClientJobItemList> arrClientJobItemList) {
        this.arrClientJobItemList = arrClientJobItemList;
    }

    public ArrayList<ClientJobTaskList> getArrClientJobTaskList() {
        return arrClientJobTaskList;
    }

    public void setArrClientJobTaskList(ArrayList<ClientJobTaskList> arrClientJobTaskList) {
        this.arrClientJobTaskList = arrClientJobTaskList;
    }

    public ArrayList<ClientJobEquipmentList> getArrClientJobEquipmentList() {
        return arrClientJobEquipmentList;
    }

    public void setArrClientJobEquipmentList(ArrayList<ClientJobEquipmentList> arrClientJobEquipmentList) {
        this.arrClientJobEquipmentList = arrClientJobEquipmentList;
    }

    public ArrayList<ClientJobCrewList> getArrClientJobCrewList() {
        return arrClientJobCrewList;
    }

    public void setArrClientJobCrewList(ArrayList<ClientJobCrewList> arrClientJobCrewList) {
        this.arrClientJobCrewList = arrClientJobCrewList;
    }
}
