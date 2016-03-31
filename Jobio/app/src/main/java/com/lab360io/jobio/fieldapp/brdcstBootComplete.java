package com.lab360io.jobio.fieldapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.crashlytics.android.Crashlytics;

import entity.ClientLocationTrackingInterval;
import io.fabric.sdk.android.Fabric;
import utility.Logger;
import utility.MyFunction;

/**
 * Created by SAI on 3/15/2016.
 */
public class brdcstBootComplete extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Fabric.with(context, new Crashlytics());
        int intIntervalTime = MyFunction.getIntPreference(context, ClientLocationTrackingInterval.Fields.LOCATION_TRACKING_INTERVAL, 0);
        Logger.debug("........................in brdcstBootComplete:"+intIntervalTime);
        MyFunction.startBackgroundService(context);
        MyFunction.scheduleLocationService(context, intIntervalTime);
    }
}
