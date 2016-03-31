package adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.lab360io.jobio.fieldapp.R;

import java.util.ArrayList;

import entity.ClientJobEquipmentList;

/**
 * Created by SAI on 3/9/2016.
 */
public class EquipmentPickupListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ClientJobEquipmentList> arrClientJobEquipment;

    public EquipmentPickupListAdapter(Context mContext, ArrayList<ClientJobEquipmentList> arrClientJobEquipment) {
        this.mContext = mContext;
        this.arrClientJobEquipment = arrClientJobEquipment;
    }
    private class ViewHolder {
        TextView txtName;
        CheckBox chk;
    }
    @Override
    public int getCount() {
        return arrClientJobEquipment.size();
    }

    @Override
    public Object getItem(int position) {
        return arrClientJobEquipment.get(position);
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
            convertView = DataBindingUtil.inflate(mInflater, R.layout.dlg_equipment_pickup_list_item, null, true).getRoot();
            //convertView = mInflater.inflate(R.layout.dlg_equipment_pickup_list_item, null);
            holder = new ViewHolder();
            holder.txtName = (TextView) convertView
                    .findViewById(R.id.txtName);
            holder.chk = (CheckBox) convertView.findViewById(R.id.chk);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientJobEquipmentList objClinetJobEquipmentList = arrClientJobEquipment.get(position);
        holder.txtName.setText(objClinetJobEquipmentList.getEquipment_name());
        //holder.chk.setChecked(objClinetJobEquipmentList.getEquipment_status());
        return convertView;
    }
}
