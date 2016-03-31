package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.lab360io.jobio.fieldapp.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import entity.ClientJobContact;
import entity.ClientJobLocation;
import entity.Job;
import utility.Logger;

/**
 * Created by SAI on 3/4/2016.
 */
public class JobListChildAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<Job> arrJob;

    private class ViewHolder {
        ImageView imgStatus;
        TextView txtTime, txtStatus, txtJobTitle, txtJobDescription, txtJobTime;
        ImageButton btnEquipmentPickupList, btnItemPickupList, btnTaskList, btnCrewList, btnLocation, btnCustomerDetail;
    }

    public JobListChildAdapter(Context mContext, ArrayList<Job> arrJob) {
        this.mContext = mContext;
        this.arrJob = arrJob;
    }

    @Override
    public int getCount() {
        return arrJob.size();
    }

    @Override
    public Object getItem(int position) {
        return arrJob.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        LayoutInflater mInflater = (LayoutInflater) mContext
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.job_list_item, null);
            holder = new ViewHolder();
            holder.imgStatus = (ImageView) convertView
                    .findViewById(R.id.imgStatus);
            holder.txtJobDescription = (TextView) convertView
                    .findViewById(R.id.txtJobDescription);
            holder.txtJobTime = (TextView) convertView
                    .findViewById(R.id.txtJobTime);
            holder.txtJobTitle = (TextView) convertView
                    .findViewById(R.id.txtJobTitle);
            holder.txtStatus = (TextView) convertView
                    .findViewById(R.id.txtStatus);
            holder.txtTime = (TextView) convertView
                    .findViewById(R.id.txtTime);
            holder.btnEquipmentPickupList = (ImageButton) convertView.findViewById(R.id.btnEquipmentPickupList);
            holder.btnItemPickupList = (ImageButton) convertView.findViewById(R.id.btnItemPickupList);
            holder.btnTaskList = (ImageButton) convertView.findViewById(R.id.btnTaskList);
            holder.btnCrewList = (ImageButton) convertView.findViewById(R.id.btnCrewList);
            holder.btnLocation = (ImageButton) convertView.findViewById(R.id.btnLocation);
            holder.btnCustomerDetail = (ImageButton) convertView.findViewById(R.id.btnCustomerDetail);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Job objJob = arrJob.get(position);
        holder.txtJobDescription.setText(objJob.getObjClientJobMaster().getDescription());
        holder.txtJobTitle.setText(objJob.getObjClientJobMaster().getJobName());
        Date dtStart = objJob.getObjClientJobMaster().getScheduledStartDateTime();
        Date dtEnd = objJob.getObjClientJobMaster().getScheduledEndDateTime();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm a");
        holder.txtJobTime.setText(dateFormat.format(dtStart) + " " + mContext.getString(R.string.strTo) + " " + dateFormat.format(dtEnd));
        holder.txtTime.setText(dateFormat.format(dtStart));

        holder.btnEquipmentPickupList.setOnClickListener(btnEquipmentPickupListClick);
        holder.btnEquipmentPickupList.setTag(position);

        holder.btnItemPickupList.setOnClickListener(btnItemPickupList);
        holder.btnItemPickupList.setTag(position);

        holder.btnTaskList.setOnClickListener(btnTaskList);
        holder.btnTaskList.setTag(position);

        holder.btnCrewList.setOnClickListener(btnCrewList);
        holder.btnCrewList.setTag(position);

        holder.btnCustomerDetail.setOnClickListener(btnCustomerDetail);
        holder.btnCustomerDetail.setTag(position);

        holder.btnLocation.setOnClickListener(btnLocation);
        holder.btnLocation.setTag(position);
        return convertView;
    }

    View.OnClickListener btnEquipmentPickupListClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("Position:" + position);
            boolean wrapInScrollView = false;
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.dlg_equipment_pickup_list, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvlEquipmentList = (ListView) view.findViewById(R.id.lvlEquipmentList);
            lvlEquipmentList.setAdapter(new EquipmentPickupListAdapter(mContext, arrJob.get(position).getArrClientJobEquipmentList()));
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnItemPickupList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("Position:" + position);
            boolean wrapInScrollView = false;
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.dlg_item_pickup_list, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlItemPickup);
            lvl.setAdapter(new ItemPickupListAdapter(mContext, arrJob.get(position).getArrClientJobItemList()));
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnTaskList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("Position:" + position);
            boolean wrapInScrollView = false;
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.dlg_task_list, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlTaskList);
            lvl.setAdapter(new TaskListAdapter(mContext, arrJob.get(position).getArrClientJobTaskList()));
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnCrewList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("position:" + position);
            boolean wrapInScrollView = false;
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.dlg_crew_member, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            ListView lvl = (ListView) view.findViewById(R.id.lvlCrewMember);
            lvl.setAdapter(new CrewListAdapter(mContext, arrJob.get(position).getArrClientJobCrewList()));
            Logger.debug("After");
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    md.dismiss();
                }
            });
        }
    };

    View.OnClickListener btnLocation = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("Position:" + position);
        }
    };

    View.OnClickListener btnCustomerDetail = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int position = Integer.parseInt(v.getTag().toString());
            Logger.debug("Position:" + position);
            boolean wrapInScrollView = false;
            final MaterialDialog md = new MaterialDialog.Builder(mContext)
                    .customView(R.layout.dlg_customer_detail, wrapInScrollView)
                    .show();
            View view = md.getCustomView();
            ImageButton btnClose = (ImageButton) view.findViewById(R.id.btnClose);
            TextView txtCustomerName = (TextView) view.findViewById(R.id.txtCustomerName);
            TextView txtAddress = (TextView) view.findViewById(R.id.txtAddress);
            TextView txtPhoneNumber = (TextView) view.findViewById(R.id.txtPhoneNumber);
            ImageButton btnChat = (ImageButton) view.findViewById(R.id.btnChat);
            ImageButton btnPhone = (ImageButton) view.findViewById(R.id.btnPhone);
            ClientJobContact objClientJobContact = arrJob.get(position).getObjContact();
            ClientJobLocation objClientJobLocation = arrJob.get(position).getObjLocation();
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
}
