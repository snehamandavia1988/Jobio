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

import entity.ClientJobCrewList;

/**
 * Created by SAI on 3/9/2016.
 */
public class CrewListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ClientJobCrewList> arrClientJobCrewList;

    public CrewListAdapter(Context mContext, ArrayList<ClientJobCrewList> arrClientJobCrewList) {
        this.mContext = mContext;
        this.arrClientJobCrewList = arrClientJobCrewList;
    }

    private class ViewHolder {
        TextView txtName;
    }

    @Override
    public int getCount() {
        return arrClientJobCrewList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrClientJobCrewList.get(position);
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
            //convertView = mInflater.inflate(R.layout.dlg_crew_member_item, null);
            convertView = DataBindingUtil.inflate(mInflater, R.layout.dlg_crew_member_item, null, true).getRoot();
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView
                    .findViewById(R.id.txtMemberName);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientJobCrewList objClinetJobCrewList = arrClientJobCrewList.get(position);
        holder.txtName.setText(objClinetJobCrewList.getEmployeeName());
        return convertView;
    }
}
