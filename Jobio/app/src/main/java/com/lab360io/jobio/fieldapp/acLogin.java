package com.lab360io.jobio.fieldapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.lab360io.jobio.fieldapp.R;
import com.lab360io.jobio.fieldapp.acHome;
import com.xwray.fontbinding.FontCache;

import org.json.JSONException;

import entity.BusinessAccountMaster;
import entity.ClientAdminUser;
import entity.ClientLoginUser;
import entity.MyLocation;
import io.fabric.sdk.android.Fabric;
import me.zhanghai.android.materialedittext.MaterialEditText;
import parser.parsQRCodeAndLoginDetail;
import utility.ConstantVal;
import utility.GPSTracker;
import utility.MyFunction;
import utility.MyNetwork;
import utility.OptionMenu;


//https://github.com/snehamandavia1988/ElectraSync2
public class acLogin extends AppCompatActivity {
    MaterialEditText edUserName, edPassword;
    Button btnLogin;
    String QRCode;
    Handler handler = new Handler();
    Context mContext;
    Button btnNotUser;
    AppCompatActivity ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        DataBindingUtil.setContentView(this, R.layout.login);
        ac = this;
        mContext = this;
        new Thread() {
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        ActionBar actionBar;
                        actionBar = ac.getSupportActionBar();
                        actionBar.setDisplayShowHomeEnabled(true);
                        actionBar.setBackgroundDrawable(new ColorDrawable(ac.getResources()
                                .getColor(R.color.tilt)));
                        View v = ac.getLayoutInflater().inflate(R.layout.home_action, null);
                        actionBar.setCustomView(v);
                        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
                        ImageButton btnOpenMenu = (ImageButton) v.findViewById(R.id.btnBottomMenu);
                        TextView txtName = (TextView) v.findViewById(R.id.txtName);
                        txtName.setText(getString(R.string.strLogin));
                        final String strBase64 = MyFunction.getStringPreference(mContext, BusinessAccountMaster.Fields.ACCOUNT_LOGO, "");
                        final String imageDataBytes = strBase64.substring(strBase64.indexOf(",") + 1);
                        Bitmap bmp = MyFunction.convertBase64ImageToBitmap(imageDataBytes);
                        btnOpenMenu.setBackgroundColor(mContext.getResources().getColor(R.color.transperant));
                        btnOpenMenu.setImageBitmap(Bitmap.createScaledBitmap(bmp, (int) mContext.getResources().getDimension(R.dimen.actionBarImageSize), (int) mContext.getResources().getDimension(R.dimen.actionBarImageSize), false));
                    }
                });
            }
        }.start();

        QRCode = MyFunction.getStringPreference(getApplicationContext(), ConstantVal.QRCODE_VALUE, "");

        btnLogin = (Button) findViewById(R.id.btnLogin);
        edUserName = (MaterialEditText) findViewById(R.id.edUserName);
        edPassword = (MaterialEditText) findViewById(R.id.edPassword);

        //set underline to clickable textview
        btnNotUser = (Button) findViewById(R.id.btnNotUser);
        btnNotUser.setPaintFlags(btnNotUser.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnNotUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear cache and clear edittext and hide NotUserName
                MyFunction.notUserName(mContext);
                btnNotUser.setVisibility(View.GONE);
                edUserName.setText("");
                edPassword.setText("");
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(mContext, acHome.class);
                startActivity(i);
                finish();*/
                boolean isDataEntedProperly = true;
                if (MyFunction.isFieldBlank(edUserName.getText().toString())) {
                    edUserName.setError(getString(R.string.strEnterUserName));
                    requestFocus(edUserName);
                    isDataEntedProperly = false;
                } else if (MyFunction.isFieldBlank(edPassword.getText().toString())) {
                    edPassword.setError(getString(R.string.strEnterPassword));
                    requestFocus(edPassword);
                    isDataEntedProperly = false;
                }
                if (isDataEntedProperly) {
                    final MyNetwork objMyNework = new MyNetwork();
                    double lat = 0, lon = 0;
                    GPSTracker gps = new GPSTracker(mContext);
                    MyLocation loc = gps.getLastLocation();
                    if (loc != null) {
                        lon = loc.getLongitude();
                        lat = loc.getLatitude();
                    }
                    final String deviceName = Build.DEVICE + " " + Build.MODEL;
                    final String deviceVersion = Build.VERSION.RELEASE + "(" + Build.VERSION.SDK_INT + ")";
                    final String strUserName = edUserName.getText().toString();
                    final String strPassword = edPassword.getText().toString();
                    final String strLocation = lat + "," + lon;
                    final ProgressDialog pd = new ProgressDialog(mContext, ProgressDialog.STYLE_HORIZONTAL);
                    //Logger.debug(strUserName + " " + strPassword + " " + QRCode + " " + strLocation + " " + deviceName + " " + deviceVersion);
                    pd.setMessage(getString(R.string.strLoading));
                    pd.show();
                    new Thread() {
                        public void run() {
                            //verify login detail
                            String result = Html.fromHtml(objMyNework.getDataFromWebAPI(mContext, ConstantVal.getLoginCredentialsUrl(mContext, QRCode),
                                    new String[]{strUserName, strPassword, strLocation, deviceName, deviceVersion}, ConstantVal.INDEX_LOGIN_API, false)).toString();
                            //Logger.debug("After login Server Response:" + result);
                            if (result != null && !result.equals("")) {
                                //result = result.substring(1, result.length() - 1).replace("\\", "");
                                try {
                                    ClientLoginUser objLoginUser = parsQRCodeAndLoginDetail.parseLogin(result);
                                    objLoginUser.setUserName(strUserName);
                                    objLoginUser.setPassword(strPassword);
                                    //objLoginUser.setQRCode(QRCode);
                                    if (objLoginUser != null) {
                                        //save verified data to shared preferences
                                        objLoginUser.saveDatatoPreference(mContext);
                                        MyFunction.startBackgroundService(mContext);
                                        Intent i = new Intent(mContext, acHome.class);
                                        startActivity(i);
                                        finish();
                                    }
                                } catch (JSONException e) {
                                    MyFunction.displaySnackbar(ac, result);
                                }
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    pd.dismiss();
                                }
                            });
                        }
                    }.start();


                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //get user name value from preferences and set to edit text.
        String strUserName = MyFunction.getStringPreference(mContext, ClientAdminUser.Fields.USER_NAME, "");
        if (!strUserName.equals("")) {
            edUserName.setText(strUserName);
            btnNotUser.setVisibility(View.VISIBLE);
        } else {
            btnNotUser.setVisibility(View.GONE);
        }
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    OptionMenu objOptionMenu = new OptionMenu();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        objOptionMenu.getCommonMenu(this, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        objOptionMenu.handleMenuItemClick(this, item);
        return super.onOptionsItemSelected(item);
    }
}
