package services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.crashlytics.android.Crashlytics;

import java.util.Date;

import entity.ClientAdminUser;
import entity.ClientFieldLocation;
import entity.MyLocation;
import io.fabric.sdk.android.Fabric;
import utility.ConstantVal;
import utility.GPSTracker;
import utility.Logger;
import utility.MyFunction;
import utility.MyNetwork;

public class serLocationTracker extends Service {
    Context mContext;
    public static boolean isServiceRunning = false;

    public serLocationTracker() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Fabric.with(this, new Crashlytics());
        mContext = getApplicationContext();
        boolean isSessionExists = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_SESSION_EXISTS, false);
        Logger.debug(".....................in serLocationTracker:" + new Date()+" isServiceRunning:"+isServiceRunning+" isSessionExists:"+isSessionExists);
        if(!isServiceRunning && !isSessionExists) {
            return super.onStartCommand(intent, flags, startId);
        }
        GPSTracker gps = new GPSTracker(mContext);
        final MyLocation objMyLocation = gps.getLastLocation();
        final ClientFieldLocation objClientFieldLocation = new ClientFieldLocation(objMyLocation);

        objClientFieldLocation.setUserId(MyFunction.getIntPreference(mContext, ClientAdminUser.Fields.ADMINUSERID, 0));
        objClientFieldLocation.setAppType("F");
        objClientFieldLocation.setJobId(0);
        if (objMyLocation != null) {
            objClientFieldLocation.setReverseGeoCodeName(objMyLocation.getAddressFromLocation(mContext));
        } else {
            objClientFieldLocation.setReverseGeoCodeName("Unable to get last location.");
        }
        //objClientFieldLocation.display();
        new Thread() {
            public void run() {
                if (objMyLocation != null) {
                    final int tokenId = MyFunction.getIntPreference(mContext, ConstantVal.TOKEN_ID, 0);
                    MyNetwork objMyNetwork = new MyNetwork();
                    objMyNetwork.getDataFromWebAPI(mContext, ConstantVal.getSaveUserLocationURL(mContext),
                            new String[]{String.valueOf(objClientFieldLocation.getObjMyLocation().getLatitude()),
                                    String.valueOf(objClientFieldLocation.getObjMyLocation().getLongitude()),
                                    objClientFieldLocation.getObjMyLocation().getGpsType(),
                                    objClientFieldLocation.getReverseGeoCodeName(),
                                    String.valueOf(objClientFieldLocation.getUserId()),
                                    objClientFieldLocation.getAppType(),
                                    String.valueOf(objClientFieldLocation.getJobId()), String.valueOf(tokenId)}
                            , ConstantVal.INDEX_SAVE_USER_LOCATION, true);
                }
            }
        }.start();
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
