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

import entity.ClientJobItemList;

/**
 * Created by SAI on 3/9/2016.
 */
public class ItemPickupListAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<ClientJobItemList> arrJobItemList;

    private class ViewHolder {
        TextView txtItem, txtQty, txtType;
        CheckBox chk;
    }

    public ItemPickupListAdapter(Context mContext, ArrayList<ClientJobItemList> arrJobItemList) {
        this.mContext = mContext;
        this.arrJobItemList = arrJobItemList;
    }

    @Override
    public int getCount() {
        return arrJobItemList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrJobItemList.get(position);
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
            //convertView = mInflater.inflate(R.layout.dlg_item_pickup_list_item, null);
            convertView = DataBindingUtil.inflate(mInflater, R.layout.dlg_item_pickup_list_item, null, false).getRoot();
            holder = new ViewHolder();
            holder.txtItem = (TextView) convertView
                    .findViewById(R.id.txtItem);
            holder.txtType = (TextView) convertView
                    .findViewById(R.id.txtType);
            holder.txtQty = (TextView) convertView
                    .findViewById(R.id.txtQty);
            holder.chk = (CheckBox) convertView.findViewById(R.id.chk);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ClientJobItemList objClinetJobItemList = arrJobItemList.get(position);
        holder.txtItem.setText(mContext.getString(R.string.strItem) + ": " + objClinetJobItemList.getRefId());
        holder.txtType.setText(mContext.getString(R.string.strType) + ": " + objClinetJobItemList.getRefType());
        holder.txtQty.setText(mContext.getString(R.string.strQty) + ": " + objClinetJobItemList.getQty());
        holder.chk.setChecked(objClinetJobItemList.isStatus());
        return convertView;
    }
}
