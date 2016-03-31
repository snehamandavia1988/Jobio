package utility;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.preference.PreferenceManager;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.lab360io.jobio.fieldapp.R;
import com.lab360io.jobio.fieldapp.acHome;
import com.lab360io.jobio.fieldapp.acJob;
import com.lab360io.jobio.fieldapp.acLogin;

import services.serBackSync;
import services.serLocationTracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import entity.BusinessAccountMaster;
import entity.BusinessAccountdbDetail;
import entity.ClientAdminUser;
import entity.ClientAdminUserAppsRel;
import entity.ClientEmployeeMaster;
import entity.ClientLocationTrackingInterval;
import parser.parsQRCodeAndLoginDetail;

public class MyFunction {
    public static void clearPreference(Context c, String key) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.remove(key);
        e.commit();
    }

    public static void setStringPreference(Context c, String pref, String val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putString(pref, val);
        e.commit();
    }

    public static String getStringPreference(Context context, String pref,
                                             String def) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(pref, def);
    }

    public static int getIntPreference(Context context, String pref, int def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getInt(
                pref, def);
    }

    public static void setIntPreference(Context c, String pref, int val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putInt(pref, val);
        e.commit();
    }

    public static boolean getBooleanPreference(Context context, String pref,
                                               boolean def) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(pref, def);
    }

    public static void setBooleanPreference(Context c, String pref, boolean val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putBoolean(pref, val);
        e.commit();
    }

    public static void setFloatPreference(Context c, String pref, float val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putFloat(pref, val);
        e.commit();
    }

    public static float getFlaotPreference(Context context, String pref,
                                           float def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getFloat(
                pref, def);
    }

    public static void setLongPreference(Context c, String pref, long val) {
        Editor e = PreferenceManager.getDefaultSharedPreferences(c).edit();
        e.putLong(pref, val);
        e.commit();
    }

    public static long getLongPreference(Context context, String pref,
                                         long def) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(
                pref, def);
    }

    public static boolean isValidEmailId(String emailID) {
        boolean isValid = true;
        Pattern pattern = Pattern.compile(".+@.+\\\\.[a-z]+");
        Matcher matcher = pattern.matcher(emailID);
        boolean matchFound = matcher.matches();
        if (emailID == null || emailID.compareTo("") == 0) {
            isValid = false;
        } else {
            if (!matchFound) {
                isValid = false;
            }
        }
        return isValid;
    }

    public static boolean isFieldBlank(String val) {
        if (val.equals("") || val == null) {
            return true;
        }
        return false;
    }

    public static boolean isSdPresent() {
        return android.os.Environment.MEDIA_MOUNTED.equals("mounted");
    }

    public static void startBackgroundService(Context mContext) {
        if (!serBackSync.isServiceRunning) {
            AlarmManager am = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
            Intent i = new Intent(mContext, serBackSync.class);
            PendingIntent pi = PendingIntent.getService(mContext, 0, i, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, 0, 60000, pi);
            serBackSync.isServiceRunning = true;
        }
    }

    public static void scheduleLocationService(Context mContext, int intervalTime) {
        //Convert minute to millisecond
        intervalTime = 1 * 1000 * 60 * intervalTime;
        if (!serLocationTracker.isServiceRunning) {
            AlarmManager am = (AlarmManager) mContext.getSystemService(mContext.ALARM_SERVICE);
            Intent i = new Intent(mContext, serLocationTracker.class);
            PendingIntent pi = PendingIntent.getService(mContext, 1, i, 0);
            am.setRepeating(AlarmManager.RTC_WAKEUP, 0, intervalTime, pi);
            serLocationTracker.isServiceRunning = true;
        }
    }

    public boolean isTopMenuVisible = false;

    public void setActionBar(final AppCompatActivity ac, final String strOpenText, final String strCloseText) {
        final Handler handler = new Handler();
        new Thread() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ac.invalidateOptionsMenu();
                        ActionBar actionBar;
                        actionBar = ac.getSupportActionBar();
                        actionBar.setDisplayShowHomeEnabled(true);
                        actionBar.setBackgroundDrawable(new ColorDrawable(ac.getResources()
                                .getColor(R.color.tilt)));
                        View v = DataBindingUtil.inflate(ac.getLayoutInflater(), R.layout.home_action, null, true).getRoot();
                        //View v = ac.getLayoutInflater().inflate(R.layout.home_action, null);
                        actionBar.setCustomView(v);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                        final RelativeLayout rlMenu = (RelativeLayout) ac.findViewById(R.id.rlMenu);
                        setMenuBackground(ac, rlMenu);
                        ImageButton btnOpenMenu = (ImageButton) v.findViewById(R.id.btnBottomMenu);
                        ImageButton btnCloseMenu = (ImageButton) rlMenu.findViewById(R.id.btnCloseMenu);
                        TextView txtName = (TextView) v.findViewById(R.id.txtName);
                        ImageButton btnJob = (ImageButton) rlMenu.findViewById(R.id.btnJob);
                        ImageButton btnExit = (ImageButton) rlMenu.findViewById(R.id.btnExit);
                        if (isTopMenuVisible) {
                            btnOpenMenu.setVisibility(View.GONE);
                            txtName.setText(strOpenText);
                        } else {
                            btnOpenMenu.setVisibility(View.VISIBLE);
                            txtName.setText(strCloseText);
                        }
                        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation slideDown = AnimationUtils.loadAnimation(ac.getApplicationContext(), R.anim.slide_down);
                                if (rlMenu.getVisibility() == View.GONE) {
                                    rlMenu.startAnimation(slideDown);
                                    rlMenu.setVisibility(View.VISIBLE);
                                    isTopMenuVisible = true;
                                    try {
                                        Thread.sleep(500);
                                        setActionBar(ac, strOpenText, strCloseText);
                                    } catch (InterruptedException e) {
                                    }
                                }
                            }
                        });
                        btnCloseMenu.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Animation slideUp = AnimationUtils.loadAnimation(ac.getApplicationContext(), R.anim.slide_up);
                                rlMenu.startAnimation(slideUp);
                                isTopMenuVisible = false;
                                rlMenu.setVisibility(View.GONE);
                                try {
                                    Thread.sleep(500);
                                    setActionBar(ac, strOpenText, strCloseText);
                                } catch (InterruptedException e) {
                                }
                            }
                        });
                        btnJob.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent i = new Intent(ac.getApplicationContext(), acJob.class);
                                ac.startActivityForResult(i, ConstantVal.EXIT_REQUEST_CODE);
                            }
                        });
                        btnExit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ac.setResult(ConstantVal.EXIT_RESPONSE_CODE);
                                ac.finish();
                            }
                        });
                    }
                });
            }
        }.start();
    }

    private void setMenuBackground(final AppCompatActivity ac, RelativeLayout rlMenu) {
        LinearLayout lyJob = (LinearLayout) rlMenu.findViewById(R.id.lyJob);
        TextView txtJob = (TextView) rlMenu.findViewById(R.id.txtJob);
        ImageButton btnJob = (ImageButton) rlMenu.findViewById(R.id.btnJob);
        setBackgroundonTouch(ac, btnJob, lyJob, txtJob, R.drawable.ic_job, R.drawable.ic_job_selected);

        LinearLayout lyInquiry = (LinearLayout) rlMenu.findViewById(R.id.lyInquiry);
        TextView txtInquiry = (TextView) rlMenu.findViewById(R.id.txtInquiry);
        ImageButton btnInquiry = (ImageButton) rlMenu.findViewById(R.id.btnInquiry);
        setBackgroundonTouch(ac, btnInquiry, lyInquiry, txtInquiry, R.drawable.ic_inquiry, R.drawable.ic_inquiry_selected);

        LinearLayout lyMessage = (LinearLayout) rlMenu.findViewById(R.id.lyMessage);
        TextView txtMessage = (TextView) rlMenu.findViewById(R.id.txtMessage);
        ImageButton btnMessage = (ImageButton) rlMenu.findViewById(R.id.btnMessage);
        setBackgroundonTouch(ac, btnMessage, lyMessage, txtMessage, R.drawable.ic_message, R.drawable.ic_message_selected);

        LinearLayout lyExpense = (LinearLayout) rlMenu.findViewById(R.id.lyExpense);
        TextView txtExpense = (TextView) rlMenu.findViewById(R.id.txtExpense);
        ImageButton btnExpense = (ImageButton) rlMenu.findViewById(R.id.btnExpense);
        setBackgroundonTouch(ac, btnExpense, lyExpense, txtExpense, R.drawable.ic_expense, R.drawable.ic_expense_selected);

        LinearLayout lySetting = (LinearLayout) rlMenu.findViewById(R.id.lySetting);
        TextView txtSetting = (TextView) rlMenu.findViewById(R.id.txtSetting);
        ImageButton btnSetting = (ImageButton) rlMenu.findViewById(R.id.btnSettings);
        setBackgroundonTouch(ac, btnSetting, lySetting, txtSetting, R.drawable.ic_settings, R.drawable.ic_setting_selected);

        LinearLayout lyExit = (LinearLayout) rlMenu.findViewById(R.id.lyExit);
        TextView txtExit = (TextView) rlMenu.findViewById(R.id.txtExit);
        ImageButton btnExit = (ImageButton) rlMenu.findViewById(R.id.btnExit);
        setBackgroundonTouch(ac, btnExit, lyExit, txtExit, R.drawable.ic_exit, R.drawable.ic_exit_selected);
    }

    private void setBackgroundonTouch(final AppCompatActivity ac, final ImageButton btn, final LinearLayout ly, final TextView txt, final int img, final int imgSelected) {
        btn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    ly.setBackgroundDrawable(new ColorDrawable(ac.getResources()
                            .getColor(R.color.tilt)));
                    txt.setTextColor(ac.getResources().getColor(R.color.white));
                    btn.setBackgroundColor(ac.getResources()
                            .getColor(R.color.tilt));
                    btn.setImageResource(imgSelected);
                    switch (v.getId()) {
                        case R.id.btnJob:
                            break;
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    ly.setBackgroundDrawable(new ColorDrawable(ac.getResources()
                            .getColor(R.color.white)));
                    txt.setTextColor(ac.getResources().getColor(R.color.tilt));
                    btn.setBackgroundColor(ac.getResources()
                            .getColor(R.color.white));
                    btn.setImageResource(img);
                }
                return false;
            }
        });
    }

    public static Date convertStringToDate(String strDate, String strFormate) {
        if (!strDate.equals("null")) {
            DateFormat df = new SimpleDateFormat(strFormate);
            try {
                return df.parse(strDate);
            } catch (ParseException e) {
                e.printStackTrace();
                return new Date(0);
            }
        } else {
            return new Date(0);
        }
    }

    public static boolean setListViewHeightBasedOnItems(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();
            return true;

        } else {
            return false;
        }
    }

    public static String getTextFromDate(Context ct, Date dt) {
        String strText = "";
        Calendar realTime = Calendar.getInstance();
        realTime.setTimeInMillis(dt.getTime());
        Calendar today = Calendar.getInstance();
        Calendar tomorrow = Calendar.getInstance();
        tomorrow.add(Calendar.DATE, 1);
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DATE, -1);
        /*Logger.debug("Real time:" + realTime.get(Calendar.DATE) + "-" + realTime.get(Calendar.MONTH) + "-" + realTime.get(Calendar.YEAR));
        Logger.debug("Today:" + today.get(Calendar.DATE) + "-" + today.get(Calendar.MONTH) + "-" + today.get(Calendar.YEAR));
        Logger.debug("Tomorrow:" + tomorrow.get(Calendar.DATE) + "-" + tomorrow.get(Calendar.MONTH) + "-" + tomorrow.get(Calendar.YEAR));
        Logger.debug("Yesterday:" + yesterday.get(Calendar.DAY_OF_MONTH) + "-" + yesterday.get(Calendar.MONTH) + "-" + yesterday.get(Calendar.YEAR));*/
        if (today.get(Calendar.DATE) == realTime.get(Calendar.DATE) && today.get(Calendar.MONTH) == realTime.get(Calendar.MONTH) && today.get(Calendar.YEAR) == realTime.get(Calendar.YEAR)) {
            strText = ct.getString(R.string.strToday);
        } else if (tomorrow.get(Calendar.DATE) == realTime.get(Calendar.DATE) && tomorrow.get(Calendar.MONTH) == realTime.get(Calendar.MONTH) && tomorrow.get(Calendar.YEAR) == realTime.get(Calendar.YEAR)) {
            strText = ct.getString(R.string.strTomorrow);
        } else if (yesterday.get(Calendar.DATE) == realTime.get(Calendar.DATE) && yesterday.get(Calendar.MONTH) == realTime.get(Calendar.MONTH) && yesterday.get(Calendar.YEAR) == realTime.get(Calendar.YEAR)) {
            strText = ct.getString(R.string.strYesterday);
        } else {
            SimpleDateFormat df = new SimpleDateFormat("dd MMM, yyyy");
            strText = df.format(realTime.getTime());
            //strText = realTime.get(Calendar.DATE) + "-" + (realTime.get(Calendar.MONTH) + 1) + "-" + realTime.get(Calendar.YEAR);
        }
        return strText;
    }

    public static boolean isValidJSON(String test) {
        try {
            new JSONObject(test);
        } catch (JSONException ex) {
            // edited, to include @Arthur's comment
            // e.g. in case JSONArray is valid as well...
            try {
                new JSONArray(test);
            } catch (JSONException ex1) {
                return false;
            }
        }
        return true;
    }

    public void openLoginScreen(final Context ctx, Handler mHandler) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                Logger.debug("In openLoginScreen....");
                Intent intent = new Intent(ctx, acLogin.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                ctx.startActivity(intent);

            }
        });
    }


    public static void masterClear(Context ctx) {
        try {
            DataBase db = new DataBase(ctx);
            db.open();
            db.cleanTable(DataBase.module_Flag_int);
            db.close();
            BusinessAccountdbDetail.clearCache(ctx);
            BusinessAccountMaster.clearCache(ctx);
            ClientAdminUser.clearCache(ctx);
            ClientAdminUserAppsRel.clearCache(ctx);
            ClientEmployeeMaster.clearCache(ctx);
            ClientLocationTrackingInterval.clearCache(ctx);
            MyFunction.clearPreference(ctx, ConstantVal.TOKEN_ID);
            MyFunction.clearPreference(ctx, ConstantVal.TOKEN);
            MyFunction.clearPreference(ctx, ConstantVal.IS_QRCODE_CONFIGURE);
            MyFunction.clearPreference(ctx, ConstantVal.QRCODE_VALUE);
            MyFunction.clearPreference(ctx, ConstantVal.IS_SESSION_EXISTS);
            serBackSync.isServiceRunning = false;
            serLocationTracker.isServiceRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.debug("Error while wipe:" + e.getMessage());
        }
    }

    public static void logOutUser(Context ctx) {
        try {
            DataBase db = new DataBase(ctx);
            db.open();
            db.cleanTable(DataBase.module_Flag_int);
            db.close();
            MyFunction.clearPreference(ctx, ConstantVal.IS_SESSION_EXISTS);
            serBackSync.isServiceRunning = false;
            serLocationTracker.isServiceRunning = false;
        } catch (Exception e) {
            e.printStackTrace();
            Logger.debug("Error while wipe:" + e.getMessage());
        }
    }

    public static void notUserName(Context c) {
        try {
            /*DataBase db = new DataBase(c);
            db.open();
            db.cleanTable(DataBase.module_Flag_int);
            db.close();
            MyFunction.clearPreference(ctx, ConstantVal.TOKEN_ID);
            MyFunction.clearPreference(ctx, ConstantVal.TOKEN);
            MyFunction.clearPreference(ctx, ConstantVal.IS_SESSION_EXPIRE);
            serBackSync.isServiceRunning = false;
            serLocationTracker.isServiceRunning = false;*/

            ClientAdminUser.clearCache(c);
            ClientAdminUserAppsRel.clearCache(c);
            ClientEmployeeMaster.clearCache(c);
            ClientLocationTrackingInterval.clearCache(c);
            MyFunction.clearPreference(c, ConstantVal.TOKEN_ID);
            MyFunction.clearPreference(c, ConstantVal.TOKEN);
            MyFunction.clearPreference(c, ConstantVal.IS_SESSION_EXISTS);
        } catch (Exception e) {
            Logger.debug("Error while wipe:" + e.getMessage());
        }
    }

    public static Bitmap convertBase64ImageToBitmap(String strBase64) {
        byte[] decodedString = Base64.decode(strBase64.getBytes(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public ProgressDialog pdVerifyQrCode;

    public void verifyingQRcode(final AppCompatActivity ac, final View v, final Handler mHandler, final String strCode) {
        final Context ctx = ac;
        final MyNetwork objMyNework = new MyNetwork();
        pdVerifyQrCode = new ProgressDialog(ctx, ProgressDialog.STYLE_HORIZONTAL);
        pdVerifyQrCode.setMessage(ctx.getString(R.string.strVerifying));
        pdVerifyQrCode.show();
        new Thread() {
            public void run() {
                final String result = Html.fromHtml(objMyNework.getDataFromWebAPI(ctx, ConstantVal.getQRCodeVerificationUrl(ctx, strCode),
                        new String[]{strCode}, ConstantVal.INDEX_QRCODE_VERIFICATION_API, false)).toString();
                //Logger.debug("After login Server Response:" + result);
                if (result != null && !result.equals("")) {
                    try {
                        String photo = parsQRCodeAndLoginDetail.parseQRCodeResult(result);
                        MyFunction.setStringPreference(ctx, ConstantVal.QRCODE_VALUE, strCode);
                        MyFunction.setBooleanPreference(ctx, ConstantVal.IS_QRCODE_CONFIGURE, true);
                        MyFunction.setStringPreference(ctx, BusinessAccountMaster.Fields.ACCOUNT_LOGO, photo);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Intent i = new Intent(ac.getApplicationContext(), acLogin.class);
                                ac.setResult(100);
                                ac.startActivity(i);
                                ac.finish();
                            }
                        });
                    } catch (Exception e) {
                        e.printStackTrace();
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        pdVerifyQrCode.dismiss();
                                    }
                                });
                                displaySnackbar(ac, result);
                            }
                        });
                    }
                }
            }
        }.start();
    }

    public static void displaySnackbar(final AppCompatActivity ac, String result) {
        final Context ctx = ac;
        if (result.equals(ConstantVal.ServerResponse.SESSION_EXPIRED)) {
            Snackbar snackbar = Snackbar
                    .make(ac.findViewById(android.R.id.content), ConstantVal.ServerResponse.getMessage(ctx, result), Snackbar.LENGTH_LONG);
            snackbar.setCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar snackbar, int event) {
                    super.onDismissed(snackbar, event);
                    Intent i = new Intent(ac, acLogin.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    ac.startActivity(i);
                }
            });
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar
                    .make(ac.findViewById(android.R.id.content), ConstantVal.ServerResponse.getMessage(ctx, result), Snackbar.LENGTH_LONG);
            snackbar.show();
        }
    }

    private AppCompatActivity objAppCompatActivity;
    private BroadcastReceiver objSessionTimeoutBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.debug("brdSessionTimeOut1:" + objAppCompatActivity.getLocalClassName());
            MyFunction.displaySnackbar(objAppCompatActivity, ConstantVal.ServerResponse.SESSION_EXPIRED);
        }
    };

    public void registerSessionTimeoutBroadcast(final AppCompatActivity ac) {
        objAppCompatActivity = ac;
        objAppCompatActivity.registerReceiver(objSessionTimeoutBroadcast, new IntentFilter("jobio.io.SESSION_EXPIRE"));
    }

    public void unRegisterSesionTimeOutBroadcast(final AppCompatActivity ac) {
        objAppCompatActivity = ac;
        objAppCompatActivity.unregisterReceiver(objSessionTimeoutBroadcast);
    }

    public void registerEmployeeDetailBroadcast(final AppCompatActivity ac, BroadcastReceiver objEmployeeDetailBroadcast) {
        objAppCompatActivity = ac;
        objAppCompatActivity.registerReceiver(objEmployeeDetailBroadcast, new IntentFilter("jobio.io.EMPLOYEE_DETAIL"));
    }

    public void unRegisterEmployeeDetailBroadcast(final AppCompatActivity ac, BroadcastReceiver objEmployeeDetailBroadcast) {
        objAppCompatActivity = ac;
        objAppCompatActivity.unregisterReceiver(objEmployeeDetailBroadcast);
    }
}
