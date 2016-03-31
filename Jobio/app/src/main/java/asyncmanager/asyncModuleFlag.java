package asyncmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.text.Html;

import org.json.JSONException;

import java.util.ArrayList;

import entity.ClientAdminUser;
import entity.ModuleFlag;
import parser.parsModuleFlag;
import utility.ConstantVal;
import utility.DataBase;
import utility.Logger;
import utility.MyFunction;
import utility.MyNetwork;

/**
 * Created by SAI on 12/19/2015.
 */
public class asyncModuleFlag extends AsyncTask<Context,Void,Void> {
    Context ctx;
    @Override
    protected Void doInBackground(Context... params) {
        ctx = params[0];
        ArrayList<ModuleFlag> arrServerdata = this.getDataFromServer();
        if (arrServerdata == null)
            return null;
        DataBase db = new DataBase(params[0]);
        db.open();
        db.cleanTable(DataBase.module_Flag_int);
        for (ModuleFlag sObj : arrServerdata) {
            db.insert(DataBase.module_flag_table, DataBase.module_Flag_int, new String[]{String.valueOf(sObj.getServerPKModuleId()),
                    sObj.getModuleName()});
        }
        db.close();
        return null;
    }

    private ArrayList<ModuleFlag> getDataFromServer() {
        MyNetwork objMyNetwork = new MyNetwork();
        ArrayList<ModuleFlag> arrServerdata = new ArrayList<>();
        final int tokenId = MyFunction.getIntPreference(ctx, ConstantVal.TOKEN_ID, 0);
        String resultModuleFlag = Html.fromHtml(objMyNetwork.getDataFromWebAPI(ctx, ConstantVal.getModuleFlagUrl(ctx),
                new String[]{String.valueOf(MyFunction.getIntPreference(ctx, ClientAdminUser.Fields.ADMINUSERID, 0)), String.valueOf(tokenId)}, ConstantVal.INDEX_MODULE_FLAG_API, false)).toString();
        Logger.debug("ModuleFlag Server Response:" + resultModuleFlag);
        if (resultModuleFlag != null && !resultModuleFlag.equals("")) {
            try {
                arrServerdata = parsModuleFlag.getList(resultModuleFlag);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return arrServerdata;
    }
}
