package fragments;


import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lab360io.jobio.fieldapp.R;


import android.support.v4.app.FragmentManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;


import java.util.ArrayList;

import utility.Logger;

/**
 * Created by SAI on 2/20/2016.
 */
public class frJobMap extends Fragment {
    private GoogleMap mMap;
    Spinner spn;
    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frjobmap, container, false);
        mContext = getActivity().getApplicationContext();
        spn = (Spinner) v.findViewById(R.id.spnDate);
        setUpMapIfNeeded();
        spn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                try {
                    mMap.clear();
                    ArrayList<String> arrLatlog = frJobList.arrCollapseHeaderList.get(position).getLatLog();
                    ArrayList<String> arrAddress = frJobList.arrCollapseHeaderList.get(position).getJobaddress();
                    ArrayList<String> arrName = frJobList.arrCollapseHeaderList.get(position).getJobName();
                    ArrayList<String> arrTime = frJobList.arrCollapseHeaderList.get(position).getJobTime();
                    ArrayList<String> arrContact = frJobList.arrCollapseHeaderList.get(position).getContactNumber();
                    ArrayList<LatLng> arrFinalLatlog = new ArrayList<LatLng>();
                    for (int i = 0; i < arrLatlog.size(); i++) {
                        String strLatlog = arrLatlog.get(i);
                        Double dblLat = Double.parseDouble(strLatlog.split(",")[0]);
                        Double dbllog = Double.parseDouble(strLatlog.split(",")[1]);
                        LatLng latLng = new LatLng(dblLat, dbllog);
                        arrFinalLatlog.add(latLng);
                        //String strSnippet = getString(R.string.strTime) + ":" + arrTime.get(i) + "<br/>" + getString(R.string.strContactnumber) + ":" + arrContact.get(i) + "<br/>" + getString(R.string.strAddress) + ":" + arrAddress.get(i);
                        setUpMap(latLng, arrName.get(i), arrTime.get(i), arrContact.get(i), arrAddress.get(i));
                    }
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    for (LatLng marker : arrFinalLatlog) {
                        builder.include(marker);
                    }
                    LatLngBounds bounds = builder.build();
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 10));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return v;
    }

    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map

        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map)).getMap();
        }
    }


    void setUpMap(LatLng latLng, final String strJobName, final String time, final String contact, final String address) {
        if (mMap != null) {
            try {

                //MAP_TYPE_NORMAL: Basic map with roads.
                //MAP_TYPE_SATELLITE: Satellite view with roads.
                //MAP_TYPE_TERRAIN: Terrain view without roads.
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.addMarker(new MarkerOptions()
                        .position(latLng)
                                //.title(strJobName)
                                //.snippet(address)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    @Override
                    public View getInfoContents(Marker marker) {
                        LayoutInflater mInflater = (LayoutInflater) mContext
                                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                        View v = DataBindingUtil.inflate(mInflater, R.layout.info_google_map, null, true).getRoot();
                        //View v = mInflater.inflate(R.layout.info_google_map, null);
                        ((TextView) v.findViewById(R.id.title)).setText(strJobName);
                        ((TextView) v.findViewById(R.id.time)).setText(getString(R.string.strTime) + ": " + time);
                        ((TextView) v.findViewById(R.id.number)).setText(getString(R.string.strContact) + ": " +contact);
                        ((TextView) v.findViewById(R.id.address)).setText(getString(R.string.strAddress) + ": " +address);
                        return v;
                    }
                });
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
