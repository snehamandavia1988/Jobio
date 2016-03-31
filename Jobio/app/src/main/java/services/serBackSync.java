package services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;

import com.crashlytics.android.Crashlytics;

import java.util.Date;

import asyncmanager.asyncJobList;
import asyncmanager.asyncLocationTrackingInterval;
import asyncmanager.asyncModuleFlag;
import asyncmanager.asyncUserData;
import io.fabric.sdk.android.Fabric;
import utility.ConstantVal;
import utility.Logger;
import utility.MyFunction;

public class serBackSync extends Service {
    Context mContext;
    public static boolean isServiceRunning = false;
    Handler mHandler = new Handler();

    public serBackSync() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Fabric.with(this, new Crashlytics());
        mContext = getApplicationContext();
        boolean isSessionExists = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_SESSION_EXISTS, false);
        Logger.debug(".....................in serBackSync:" + new Date() + " isServiceRunning:" + isServiceRunning + " isSessionExists:" + isSessionExists);
        if (!isServiceRunning && !isSessionExists) {
            return super.onStartCommand(intent, flags, startId);
        }
        asyncUserData objAsyncUserData = new asyncUserData();
        objAsyncUserData.execute(mContext);
        /*try {
            objAsyncUserData.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        asyncModuleFlag objAsyncModuleFlag = new asyncModuleFlag();
        objAsyncModuleFlag.execute(mContext);
        asyncLocationTrackingInterval objAsyncLocationTrackingInterval = new asyncLocationTrackingInterval();
        objAsyncLocationTrackingInterval.execute(mContext);
        asyncJobList objAsyncJobList = new asyncJobList();
        objAsyncJobList.execute(mContext);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isServiceRunning = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isServiceRunning = false;
    }
}
