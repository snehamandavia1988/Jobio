package adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lab360io.jobio.fieldapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import entity.ClientJobContact;
import entity.ClientJobLocation;
import entity.Job;
import utility.Logger;

/**
 * Created by SAI on 2/18/2016.
 */

public class JobListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<String> listDataHeader;
    private HashMap<String, List<Job>> listDataChild;

    public JobListAdapter(Context context, List<String> listDataHeader,
                          HashMap<String, List<Job>> listChildData) {
        this.mContext = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.listDataChild.get(this.listDataHeader.get(groupPosition))
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    private class ViewHolderHeader {
        TextView txtDate;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolderHeader holderHeader = null;
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            holderHeader = new ViewHolderHeader();
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            //convertView = infalInflater.inflate(R.layout.job_list_group_header, null);
            convertView = DataBindingUtil.inflate(infalInflater, R.layout.job_list_group_header, null, true).getRoot();
            holderHeader.txtDate = (TextView) convertView
                    .findViewById(R.id.txtHeader);
            convertView.setTag(holderHeader);
        } else {
            holderHeader = (ViewHolderHeader) convertView.getTag();
        }

        /*TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.txtHeader);*/
        //String strDate = MyFunction.getTextFromDate(mContext, arr.get(0).getObjClientJobMaster().getScheduledStartDateTime());
        holderHeader.txtDate.setText(headerTitle);
        return convertView;
    }

    private class ViewHolderChild {
        ImageView imgStatus;
        TextView txtJobDescription, txtJobTime, txtJobTitle, txtStatus, txtTime;
        ImageButton btnEquipmentPickupList, btnItemPickupList, btnTaskList, btnCrewList, btnLocation, btnCustomerDetail;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Job objJob = (Job) getChild(groupPosition, childPosition);
        ViewHolderChild holderChild;
        if (convertView == null) {
            holderChild = new ViewHolderChild();
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = DataBindingUtil.inflate(infalInflater, R.layout.job_list_item, null, true).getRoot();
            holderChild.imgStatus = (ImageView) convertView.findViewById(R.id.imgStatus);
            holderChild.txtJobDescription = (TextView) convertView.findViewById(R.id.txtJobDescription);
            holderChild.txtJobTime = (TextView) convertView.findViewById(R.id.txtJobTime);
            holderChild.txtJobTitle = (TextView) convertView.findViewById(R.id.txtJobTitle);
            holderChild.txtStatus = (TextView) convertView.findViewById(R.id.txtStatus);
            holderChild.txtTime = (TextView) convertView.findViewById(R.id.txtTime);
            holderChild.btnEquipmentPickupList = (ImageButton) convertView.findViewById(R.id.btnEquipmentPickupList);
            holderChild.btnItemPickupList = (ImageButton) convertView.findViewById(R.id.btnItemPickupList);
            holderChild.btnTaskList = (ImageButton) convertView.findViewById(R.id.btnTaskList);
            holderChild.btnCrewList = (ImageButton) convertView.findViewById(R.id.btnCrewList);
            holderChild.btnLocation = (ImageButton) convertView.findViewById(R.id.btnLocation);
            holderChild.btnCustomerDetail = (ImageButton) convertView.findViewById(R.id.btnCustomerDetail);
            //convertView = infalInflater.inflate(R.layout.job_list_item, null);
            convertView.setTag(holderChild);
        } else {
            holderChild = (ViewHolderChild) convertView.getTag();
        }
        holderChild.txtJobDescription.setText(objJob.getObjClientJobMaster().getDescription());
        holderChild.txtJobTitle.setText(objJob.getObjClientJobMaster().getJobName());
        Date dtStart = objJob.getObjClientJobMaster().getScheduledStartDateTime();
        Date dtEnd = objJob.getObjClientJobMaster().getScheduledEndDateTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        holderChild.txtJobTime.setText(dateFormat.format(dtStart) + " " + mContext.getString(R.string.strTo) + " " + dateFormat.format(dtEnd));
        holderChild.txtTime.setText(dateFormat.format(dtStart));

        holderChild.btnEquipmentPickupList.setOnClickListener(btnEquipmentPickupListClick);
        holderChild.btnEquipmentPickupList.setTag(new String(groupPosition + "," + childPosition));

        holderChild.btnItemPickupList.setOnClickListener(btnItemPickupListClick);
        holderChild.btnItemPickupList.setTag(new String(groupPosition + "," + childPosition));

        holderChild.btnTaskList.setOnClickListener(btnTaskListClick);
        holderChild.btnTaskList.setTag(new String(groupPosition + "," + childPosition));

        holderChild.btnCrewList.setOnClickListener(btnCrewListClick);
        holderChild.btnCrewList.setTag(new String(groupPosition + "," + childPosition));

        holderChild.btnCustomerDetail.setOnClickListener(btnCustomerDetailClick);
        holderChild.btnCustomerDetail.setTag(new String(groupPosition + "," + childPosition));

        holderChild.btnLocation.setOnClickListener(btnLocationClick);
        holderChild.btnLocation.setTag(new String(groupPosition + "," + childPosition));

        return convertView;
    }

    View.OnClickListener btnEquipmentPickupListClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            boolean wrapInScrollView = false;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = DataBindingUtil.inflate(infalInflater, R.layout.dlg_equipment_pickup_list, null, true).getRoot();
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(view1, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvlEquipmentList = (ListView) view.findViewById(R.id.lvlEquipmentList);
            lvlEquipmentList.setAdapter(new EquipmentPickupListAdapter(mContext, objJob.getArrClientJobEquipmentList()))
            ;
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnItemPickupListClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            boolean wrapInScrollView = false;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = DataBindingUtil.inflate(infalInflater, R.layout.dlg_item_pickup_list, null, true).getRoot();
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(view1, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlItemPickup);
            lvl.setAdapter(new ItemPickupListAdapter(mContext, objJob.getArrClientJobItemList()));
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnTaskListClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            boolean wrapInScrollView = false;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = DataBindingUtil.inflate(infalInflater, R.layout.dlg_task_list, null, true).getRoot();
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(view1, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlTaskList);
            lvl.setAdapter(new TaskListAdapter(mContext, objJob.getArrClientJobTaskList()));
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnCrewListClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            boolean wrapInScrollView = false;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = DataBindingUtil.inflate(infalInflater, R.layout.dlg_crew_member, null, true).getRoot();
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(view1, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlCrewMember);
            lvl.setAdapter(new CrewListAdapter(mContext, objJob.getArrClientJobCrewList()));
            Logger.debug("After");
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnLocationClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            String strCoordinates = objJob.getObjLocation().getCoordinates();
            String[] latLog = strCoordinates.split(",");
            if (latLog.length == 2) {
                try {
                    //Uri gmmIntentUri = Uri.parse("google.navigation:q=Taronga+Zoo,+Sydney+Australia");
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=" + latLog[0] + "," + latLog[1]);
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mContext.startActivity(mapIntent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };

    View.OnClickListener btnCustomerDetailClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String strTag = v.getTag().toString();
            Logger.debug("Position:" + strTag);
            String[] intGroupChildPosition = strTag.split(",");
            int intGroupPosition = Integer.parseInt(intGroupChildPosition[0]);
            int intChildPosition = Integer.parseInt(intGroupChildPosition[1]);
            Job objJob = (Job) getChild(intGroupPosition, intChildPosition);
            boolean wrapInScrollView = false;
            LayoutInflater infalInflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view1 = DataBindingUtil.inflate(infalInflater, R.layout.dlg_customer_detail, null, true).getRoot();
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(view1, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            TextView txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtPhoneNumber = (TextView) view.findViewById(R.id.txtPhoneNumber);
            ImageButton btnChat = (ImageButton) view.findViewById(R.id.btnChat);
            ImageButton btnPhone = (ImageButton) view.findViewById(R.id.btnPhone);
            ClientJobContact objClientJobContact = objJob.getObjContact();
            ClientJobLocation objClientJobLocation = objJob.getObjLocation();
            txtCustomerName.setText(objClientJobContact.getContactName());
            String strAddress = objClientJobLocation.getHouse_no() + ", " + objClientJobLocation.getStreet() + ", " + objClientJobLocation.getLandmark() + ", " + objClientJobLocation.getCity() + ", " + objClientJobLocation.getState() + ", " + objClientJobLocation.getCountry() + ", " + objClientJobLocation.getPostcode();
            txtAddress.setText(strAddress);
            txtPhoneNumber.setText(objClientJobContact.getContactNo());
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };


    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}

