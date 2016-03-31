package asyncmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;

import org.json.JSONException;

import entity.ClientAdminUser;
import entity.UserData;
import parser.parsUserData;
import utility.ConstantVal;
import utility.MyFunction;
import utility.MyNetwork;

/**
 * Created by SAI on 1/1/2016.
 */
public class asyncUserData extends AsyncTask<Context,Void,Void>{
    @Override
    protected Void doInBackground(Context... params) {
        UserData objUserData = new UserData();
        MyNetwork objMyNetwork = new MyNetwork();
        final int tokenId = MyFunction.getIntPreference(params[0], ConstantVal.TOKEN_ID, 0);
        String userName = MyFunction.getStringPreference(params[0], ClientAdminUser.Fields.USER_NAME, "");
        String password = MyFunction.getStringPreference(params[0], ClientAdminUser.Fields.PASSWORD, "");
        String qrcode = MyFunction.getStringPreference(params[0], ConstantVal.QRCODE_VALUE, "");
        String result = objMyNetwork.getDataFromWebAPI(params[0], ConstantVal.getUserDataUrl(params[0]), new String[]{userName, password, qrcode, String.valueOf(tokenId)}, ConstantVal.INDEX_USER_DATA_API, false);
        if (result != null && !result.equals("")) {
            try {
                objUserData = parsUserData.parseJSON(result);
                objUserData.getObjClientAdminUser().setPassword(password);
                objUserData.getObjBusinessAccountdbDetail().saveFiledsToPreferences(params[0]);
                objUserData.getObjBusinessAccountMaster().saveFiledsToPreferences(params[0]);
                objUserData.getObjClientAdminUser().saveFiledsToPreferences(params[0]);
                objUserData.getObjClientAdminUserAppsRel().saveFiledsToPreferences(params[0]);
                objUserData.getObjClientEmployeeMaster().saveFiledsToPreferences(params[0]);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
/*public class asyncUserData extends Thread {
    Context ctx;
    Handler mHandler;

    public asyncUserData(Context ctx, Handler mHandler) {
        this.ctx = ctx;
        this.mHandler = mHandler;
        start();
    }

    public void run() {
        try {
            UserData objUserData = this.getDataFromServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private UserData getDataFromServer() {
        UserData objUserData = new UserData();
        MyNetwork objMyNetwork = new MyNetwork();
        final int tokenId = MyFunction.getIntPreference(ctx, ConstantVal.TOKEN_ID, 0);
        String userName = MyFunction.getStringPreference(ctx, ClientAdminUser.Fields.USER_NAME, "");
        String password = MyFunction.getStringPreference(ctx, ClientAdminUser.Fields.PASSWORD, "");
        String qrcode = MyFunction.getStringPreference(ctx, ConstantVal.QRCODE_VALUE, "");
        String result = objMyNetwork.getDataFromWebAPI(ctx, ConstantVal.getUserDataUrl(ctx), new String[]{userName, password, qrcode, String.valueOf(tokenId)}, ConstantVal.INDEX_USER_DATA_API, false);
        if (result != null && !result.equals("")) {
            try {
                objUserData = parsUserData.parseJSON(result);
                objUserData.getObjClientAdminUser().setPassword(password);
                objUserData.getObjBusinessAccountdbDetail().saveFiledsToPreferences(ctx);
                objUserData.getObjBusinessAccountMaster().saveFiledsToPreferences(ctx);
                objUserData.getObjClientAdminUser().saveFiledsToPreferences(ctx);
                objUserData.getObjClientAdminUserAppsRel().saveFiledsToPreferences(ctx);
                objUserData.getObjClientEmployeeMaster().saveFiledsToPreferences(ctx);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {

        }
        return objUserData;

    }


}*/
