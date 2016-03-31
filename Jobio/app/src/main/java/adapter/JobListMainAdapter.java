package adapter;

/**
 * Created by SAI on 3/4/2016.
 */
public class JobListMainAdapter{

}
/*public class JobListMainAdapter extends BaseAdapter {
    Context mContext;
    HashMap<Integer, ArrayList<Job>> listDataChild;

    private class ViewHolder {
        Button btn;
        ListView lvl;
    }

    public JobListMainAdapter(Context mContext, HashMap<Integer, ArrayList<Job>> listDataChild) {
        this.mContext = mContext;
        this.listDataChild = listDataChild;
    }

    @Override
    public int getCount() {
        return listDataChild.size();
    }

    @Override
    public Object getItem(int position) {
        return listDataChild.get(new Integer(position));
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
            convertView = mInflater.inflate(R.layout.job_list_group_header, null);
            holder = new ViewHolder();
            holder.btn = (Button) convertView
                    .findViewById(R.id.expandable_toggle_button);
            holder.lvl = (ListView) convertView
                    .findViewById(R.id.lvlData);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ArrayList<Job> arr = listDataChild.get(new Integer(position));
        String strDate = MyFunction.getTextFromDate(mContext, arr.get(0).getObjClientJobMaster().getScheduledStartDateTime());
       // if (strDate.equals(mContext.getString(R.string.strToday))) {
         //   Logger.debug("It's todaty:" + strDate + " Position:" + position);
           // ConstantVal.TODAY_INDEX_FROM_JOB_LIST = position;
        //}
        holder.btn.setText(strDate);
        holder.lvl.setAdapter(new JobListChildAdapter(mContext, arr));
        MyFunction.setListViewHeightBasedOnItems(holder.lvl);
        return convertView;
    }
}*/
