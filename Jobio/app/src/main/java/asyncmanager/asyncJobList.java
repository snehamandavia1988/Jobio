package asyncmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;

import java.util.ArrayList;

import entity.ClientEmployeeMaster;
import entity.Job;
import utility.ConstantVal;
import utility.Logger;
import utility.MyFunction;
import utility.MyNetwork;

/**
 * Created by SAI on 3/12/2016.
 */
public class asyncJobList extends AsyncTask<Context, Void, Void> {
    Context ctx;
    @Override
    protected Void doInBackground(Context... params) {
        this.ctx = ctx;
               /* ArrayList<Job> arrJob = getDataFromServer();
        if (arrJob != null) {

        }*/
        return null;
    }

    private ArrayList<Job> getDataFromServer() {
        MyNetwork objMyNetwork = new MyNetwork();
        final int tokenId = MyFunction.getIntPreference(ctx, ConstantVal.TOKEN_ID, 0);
        String strEmployeeID = String.valueOf(MyFunction.getIntPreference(ctx, ClientEmployeeMaster.Fields.EMPLOYEE_ID, 0));
        String result = Html.fromHtml(objMyNetwork.getDataFromWebAPI(ctx, ConstantVal.getJobList(ctx), new String[]{strEmployeeID, String.valueOf(tokenId)}, ConstantVal.INDEX_GET_JOB_LIST, false)).toString();
        return null;
    }
}
