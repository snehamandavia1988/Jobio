package asyncmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import org.json.JSONException;

import entity.ClientLocationTrackingInterval;
import parser.parsLocationTrackingInterval;
import utility.ConstantVal;
import utility.MyFunction;
import utility.MyNetwork;

import services.serLocationTracker;

/**
 * Created by SAI on 1/1/2016.
 */
public class asyncLocationTrackingInterval extends AsyncTask<Context, Void, Void> {
    Context ctx;

    @Override
    protected Void doInBackground(Context... params) {
        this.ctx = params[0];
        ClientLocationTrackingInterval objLocationTrackingInterval = getDataFromServer();
        if (objLocationTrackingInterval != null) {
            int intOldIntervalTime = MyFunction.getIntPreference(ctx, ClientLocationTrackingInterval.Fields.LOCATION_TRACKING_INTERVAL, 0);
            if (intOldIntervalTime != objLocationTrackingInterval.getPeriod()) {
                objLocationTrackingInterval.saveFiledsToPreferences(ctx);
                serLocationTracker.isServiceRunning = false;
            }
            MyFunction.scheduleLocationService(ctx, objLocationTrackingInterval.getPeriod());
        }
        return null;
    }


    private ClientLocationTrackingInterval getDataFromServer() {
        MyNetwork objMyNetwork = new MyNetwork();
        final int tokenId = MyFunction.getIntPreference(ctx, ConstantVal.TOKEN_ID, 0);
        String intervalTime = Html.fromHtml(objMyNetwork.getDataFromWebAPI(ctx, ConstantVal.getLocationTrackingIntervalURL(ctx), new String[]{String.valueOf(tokenId)}, ConstantVal.INDEX_LOCATION_TRACKING_INTERVAL, false)).toString();
        try {
            ClientLocationTrackingInterval objLocationTrackingInterval = parsLocationTrackingInterval.parseJSON(intervalTime);
            return objLocationTrackingInterval;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
