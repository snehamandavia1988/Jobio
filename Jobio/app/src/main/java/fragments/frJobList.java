package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lab360io.jobio.fieldapp.R;
import com.lab360io.jobio.fieldapp.acJob;
import com.xwray.fontbinding.FontCache;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import adapter.JobListAdapter;
import entity.ClientEmployeeMaster;
import entity.Job;
import parser.parsJobList;
import utility.ConstantVal;
import utility.Logger;
import utility.MyFunction;
import utility.MyNetwork;

/**
 * Created by SAI on 2/20/2016.
 */
public class frJobList extends Fragment {
    JobListAdapter objAdapter;
    ExpandableListView lvJob;
    Context mContext;
    Handler handler = new Handler();
    int intTodayDateHeaderPosition;
    public static ArrayList<SpinnerData> arrCollapseHeaderList = new ArrayList<SpinnerData>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        FontCache.getInstance(mContext).addFont("Ubuntu", "Ubuntu-C.ttf");
        View view = DataBindingUtil.inflate(inflater, R.layout.frjob, null, true).getRoot();
        lvJob = (ExpandableListView) view.findViewById(R.id.lvJob);
        prepareListData();

        // Logger.debug("In list Fragement................");
        lvJob.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                try {
                    String strDate = "";
                    ArrayList<String> arrLatlog = new ArrayList<String>();
                    ArrayList<String> arrAdress = new ArrayList<String>();
                    ArrayList<String> arrJobName = new ArrayList<String>();
                    ArrayList<String> arrTime = new ArrayList<String>();
                    ArrayList<String> arrContactNumber = new ArrayList<String>();
                    SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
                    for (int i = 0; i < objAdapter.getChildrenCount(groupPosition); i++) {
                        Job objJob = (Job) objAdapter.getChild(groupPosition, i);
                        Logger.debug("strDate:" + strDate);
                        Logger.debug(objJob.getObjClientJobMaster().getScheduledStartDateTime().toString());
                        if (strDate.equals("")) {
                            strDate = df.format(objJob.getObjClientJobMaster().getScheduledStartDateTime());
                        }
                        String address = objJob.getObjLocation().getHouse_no() + "," + objJob.getObjLocation().getStreet() + "," + objJob.getObjLocation().getLandmark() + "," + objJob.getObjLocation().getCity() + "," + objJob.getObjLocation().getState() + "," + objJob.getObjLocation().getCountry() + "," + objJob.getObjLocation().getPostcode();
                        arrLatlog.add(objJob.getObjLocation().getCoordinates());
                        arrAdress.add(address);
                        arrJobName.add((objJob.getObjClientJobEmployeeList().getJob_id() + "-" + objJob.getObjClientJobMaster().getJobName()).toUpperCase());
                        arrContactNumber.add(objJob.getObjContact().getContactNo());

                        Date dtStart = objJob.getObjClientJobMaster().getScheduledStartDateTime();
                        Date dtEnd = objJob.getObjClientJobMaster().getScheduledEndDateTime();
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
                        arrTime.add(dateFormat.format(dtStart) + " " + mContext.getString(R.string.strTo) + " " + dateFormat.format(dtEnd));
                    }
                    arrCollapseHeaderList.add(new SpinnerData(strDate, arrLatlog, arrAdress, arrJobName, arrTime, arrContactNumber));
                    if (groupPosition == objAdapter.getGroupCount() - 1) {
                        Spinner spn = (Spinner) getActivity().findViewById(R.id.spnDate);
                        ArrayAdapter<SpinnerData> adp = new ArrayAdapter<SpinnerData>(acJob.mContext, android.R.layout.select_dialog_item, arrCollapseHeaderList);
                        spn.setAdapter(adp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        /*lvJob.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                for (int i = 0; i < objAdapter.getChildrenCount(groupPosition); i++) {
                    Job objJob = (Job) objAdapter.getChild(groupPosition, i);
                    arrCollapseHeaderList.add(objJob.getObjLocation().getCoordinates());
                }
                setPositionToJobMap();
            }
        });

        lvJob.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                for (int i = 0; i < objAdapter.getChildrenCount(groupPosition); i++) {
                    Job objJob = (Job) objAdapter.getChild(groupPosition, i);
                    arrCollapseHeaderList.remove(objJob.getObjLocation().getCoordinates());
                }
                setPositionToJobMap();
            }
        });*/

        return view;
    }


    /*HashMap<Integer, ArrayList<Job>> listDataChild;
    private void prepareListData() {
        listDataChild = new HashMap<Integer, ArrayList<Job>>();
        final ProgressDialog pd = new ProgressDialog(mContext, ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage(getString(R.string.strLoading));
        pd.show();
        new Thread() {
            public void run() {
                //verify login detail
                final int tokenId = MyFunction.getIntPreference(mContext, ConstantVal.TOKEN_ID, 0);
                String strEmployeeID = String.valueOf(MyFunction.getIntPreference(mContext, ClientEmployeeMaster.Fields.EMPLOYEE_ID, 0));
                MyNetwork objMyNework = new MyNetwork();
                final String result = Html.fromHtml(objMyNework.getDataFromWebAPI(mContext, ConstantVal.getJobList(mContext), new String[]{strEmployeeID, String.valueOf(tokenId)}, ConstantVal.INDEX_GET_JOB_LIST, false)).toString();
                //  Logger.debug("Job list response:" + result);
                if (result != null && !result.equals("")) {
                    try {
                        ArrayList<Job> arrJob = parsJobList.parseJSON(result);
                        int headerCount = 0;
                        int mainCount = 0;
                        ArrayList<Job> chileData = null;
                        for (; mainCount < arrJob.size(); ) {
                            Date dt1 = arrJob.get(mainCount).getObjClientJobMaster().getScheduledStartDateTime();//Value update for each row.
                            String strDate = MyFunction.getTextFromDate(mContext, dt1);
                            //Logger.debug(dt1.toString() + " " + strDate);
                            if (strDate.equals(getString(R.string.strToday))) {
                                intTodayDateHeaderPosition = headerCount;
                            }
                            // Logger.debug("D1:" + dt1.toString());
                            boolean isHeaderAdded = false;
                            chileData = new ArrayList<Job>();
                            for (; mainCount < arrJob.size(); mainCount++) {
                                Date dt2 = arrJob.get(mainCount).getObjClientJobMaster().getScheduledStartDateTime();
                                if ((dt1.getDay() == dt2.getDay()) && (dt1.getMonth() == dt2.getMonth()) && (dt1.getYear() == dt2.getYear())) {
                                    if (!isHeaderAdded) {
                                        //listDataHeader.add(headerCount, dt2.toString());
                                        isHeaderAdded = true;
                                    }
                                    chileData.add(arrJob.get(mainCount));
                                    //Logger.debug("Child data add:"+dt1.toString()+"   "+dt2.toString());
                                } else {
                                    listDataChild.put(new Integer(headerCount), chileData);
                                    headerCount++;
                                    break;
                                }
                            }
                        }
                        listDataChild.put(new Integer(headerCount), chileData);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                JobListMainAdapter adp = new JobListMainAdapter(mContext, listDataChild);
                                lvJob.setAdapter(new SlideExpandableListAdapter(adp, R.id.expandable_toggle_button, R.id.expandable));
                                if (lvJob.getAdapter() != null) {
                                    try {
                                        Thread.sleep(100);
                                    } catch (InterruptedException e) {
                                    }
                                    View v = lvJob.getAdapter().getView(intTodayDateHeaderPosition, null, lvJob);
                                    ((Button) v.findViewById(R.id.expandable_toggle_button)).performClick();
                                } else {
                                }
                                pd.dismiss();
                            }
                        });
                    } catch (final Exception e) {
                        e.printStackTrace();
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pd.dismiss();
                                MyFunction.displaySnackbar((AppCompatActivity)getActivity(),result);
                            }
                        });
                    }
                }
            }
        }.start();
    }*/

    /*private void setPositionToJobMap() {
        try {
            Bundle bundle = new Bundle();
            FragmentManager fragmentManager = getFragmentManager();
            //FragmentManager fragmentManager = getChildFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            frJobMap mlf = new frJobMap();

            bundle.putStringArrayList("list", arrCollapseHeaderList);
            mlf.setArguments(bundle);
            fragmentTransaction.replace(android.R.id.tabcontent, mlf);
            fragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/

    ArrayList<String> listDataHeader;
    HashMap<String, List<Job>> listDataChild;

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<Job>>();
        final ProgressDialog pd = new ProgressDialog(mContext, ProgressDialog.STYLE_HORIZONTAL);
        pd.setMessage(getString(R.string.strLoading));
        pd.show();
        new Thread() {
            public void run() {
                //verify login detail
                final int tokenId = MyFunction.getIntPreference(mContext, ConstantVal.TOKEN_ID, 0);
                String strEmployeeID = String.valueOf(MyFunction.getIntPreference(mContext, ClientEmployeeMaster.Fields.EMPLOYEE_ID, 0));
                MyNetwork objMyNework = new MyNetwork();
                final String result = Html.fromHtml(objMyNework.getDataFromWebAPI(mContext, ConstantVal.getJobList(mContext), new String[]{strEmployeeID, String.valueOf(tokenId)}, ConstantVal.INDEX_GET_JOB_LIST, false)).toString();
                Logger.debug("Job list response:" + result);
                if (result != null && !result.equals("")) {
                    try {
                        ArrayList<Job> arrJob = parsJobList.parseJSON(result);
                        int headerCount = 0;
                        int mainCount = 0;
                        ArrayList<Job> chileData = null;
                        for (; mainCount < arrJob.size(); ) {
                            Date dt1 = arrJob.get(mainCount).getObjClientJobMaster().getScheduledStartDateTime();//Value update for each row.
                            Logger.debug("D1:" + dt1.toString());
                            boolean isHeaderAdded = false;
                            chileData = new ArrayList<Job>();
                            for (; mainCount < arrJob.size(); mainCount++) {
                                Date dt2 = arrJob.get(mainCount).getObjClientJobMaster().getScheduledStartDateTime();
                                Logger.debug("Dt2:" + dt2.toString());
                                if ((dt1.getDay() == dt2.getDay()) && (dt1.getMonth() == dt2.getMonth()) && (dt1.getYear() == dt2.getYear())) {
                                    if (!isHeaderAdded) {
                                        String strDate = MyFunction.getTextFromDate(mContext, dt2);
                                        if (strDate.equals(mContext.getString(R.string.strToday))) {
                                            intTodayDateHeaderPosition = headerCount;
                                        }
                                        listDataHeader.add(strDate);
                                        isHeaderAdded = true;
                                    }
                                    chileData.add(arrJob.get(mainCount));
                                } else {
                                    listDataChild.put(listDataHeader.get(headerCount), chileData);
                                    headerCount++;
                                    break;
                                }
                            }
                        }
                        listDataChild.put(listDataHeader.get(headerCount), chileData);
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                objAdapter = new JobListAdapter(mContext, listDataHeader, listDataChild);

                                lvJob.setAdapter(objAdapter);
                                if (lvJob.getAdapter() != null) {
                                    int count = objAdapter.getGroupCount();
                                    for (int i = 0; i < count; i++)
                                        lvJob.expandGroup(i);
                                    Logger.debug("Today date position");
                                    lvJob.setSelectedGroup(intTodayDateHeaderPosition);
                                }
                                pd.dismiss();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        MyFunction.displaySnackbar((AppCompatActivity) getActivity(), result);
                    }
                }
            }
        }.start();
    }
}
