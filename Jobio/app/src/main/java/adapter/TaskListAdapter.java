package adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lab360io.jobio.fieldapp.R;

import java.util.ArrayList;

import entity.ClientJobTaskList;

/**
 * Created by SAI on 3/9/2016.
 */
public class TaskListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ClientJobTaskList> arrClientJobTask;

    public TaskListAdapter(Context mContext, ArrayList<ClientJobTaskList> arrClientJobTask) {
        this.mContext = mContext;
        this.arrClientJobTask = arrClientJobTask;
    }

    private class ViewHolder {
        TextView txtName, txtDescription;
    }

    @Override
    public int getCount() {
        return arrClientJobTask.size();
    }

    @Override
    public Object getItem(int position) {
        return arrClientJobTask.get(position);
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
            convertView = DataBindingUtil.inflate(mInflater, R.layout.dlg_task_list_item, null, true).getRoot();
            //convertView = mInflater.inflate(R.layout.dlg_task_list_item, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView
                    .findViewById(R.id.txtTaskName);
            holder.txtDescription = (TextView) convertView.findViewById(R.id.txtDescription);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientJobTaskList objClinetJobTaskList = arrClientJobTask.get(position);
        holder.txtName.setText(objClinetJobTaskList.getTaskName());
        holder.txtDescription.setText(objClinetJobTaskList.getDescription());
        return convertView;
    }
}
