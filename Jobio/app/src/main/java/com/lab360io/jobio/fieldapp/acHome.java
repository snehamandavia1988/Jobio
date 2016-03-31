package com.lab360io.jobio.fieldapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.xwray.fontbinding.FontCache;

import entity.ClientEmployeeMaster;
import io.fabric.sdk.android.Fabric;
import utility.ConstantVal;
import utility.MyFunction;
import utility.OptionMenu;


public class acHome extends AppCompatActivity {
    Context mContext;
    MyFunction objMyFunction = new MyFunction();
    Handler handler = new Handler();
    AppCompatActivity ac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        DataBindingUtil.setContentView(this, R.layout.home_main);
        mContext = this;
        ac = this;
        String name = MyFunction.getStringPreference(mContext, ClientEmployeeMaster.Fields.FIRST_NAME, "");
        if (!name.equals("")) {
            name = getString(R.string.strHiya) + ", " + name;
        }
        objMyFunction.setActionBar(this, getString(R.string.strHome), name);
        checkTokenExistance();
    }

    private BroadcastReceiver objEmployeeDetailBroadcast = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                String name = intent.getStringExtra("name");
                if (!name.equals("")) {
                    name = getString(R.string.strHiya) + ", " + name;
                }
                objMyFunction.setActionBar(ac, getString(R.string.strHome), name);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        objMyFunction.registerSessionTimeoutBroadcast(ac);
        objMyFunction.registerEmployeeDetailBroadcast(ac, objEmployeeDetailBroadcast);
    }

    @Override
    protected void onStop() {
        super.onStop();
        objMyFunction.unRegisterSesionTimeOutBroadcast(ac);
        objMyFunction.unRegisterEmployeeDetailBroadcast(ac, objEmployeeDetailBroadcast);
    }

    OptionMenu objOptionMenu = new OptionMenu();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (objMyFunction.isTopMenuVisible) {
            getMenuInflater().inflate(R.menu.home, menu);
        } else {
            objOptionMenu.getCommonMenu(this, menu);
        }
        return true;
    }

    private void checkTokenExistance() {
        boolean isSessionExists = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_SESSION_EXISTS, false);
        if (!isSessionExists) {
            MyFunction.displaySnackbar(ac, ConstantVal.ServerResponse.SESSION_EXPIRED);
        }
        /*new Thread() {
            public void run() {
                /*final int tokenId = MyFunction.getIntPreference(mContext, ConstantVal.TOKEN_ID, 0);
                MyNetwork objMyNework = new MyNetwork();
                final String result = Html.fromHtml(objMyNework.getDataFromWebAPI(mContext, ConstantVal.getCheckTokenExits(mContext),
                        new String[]{String.valueOf(tokenId)}, ConstantVal.INDEX_CHECK_TOKEN_EXITS, false)).toString();
                try {
                    if (result != null && !result.equals("")) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (result.equals(ConstantVal.ServerResponse.SESSION_EXPIRED)) {
                                    MyFunction.displaySnackbar(ac, result);
                                }
                            }

                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuSynchronize:
                break;
            default:
                objOptionMenu.handleMenuItemClick(this, item);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ConstantVal.EXIT_REQUEST_CODE && resultCode == ConstantVal.EXIT_RESPONSE_CODE) {
            finish();
        }
    }

    /* public void setActionBar() {
        invalidateOptionsMenu();
        android.support.v7.app.ActionBar actionBar;
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.tilt)));
        View v = getLayoutInflater().inflate(R.layout.home_action, null);
        actionBar.setCustomView(v);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        btnOpenMenu = (ImageButton) v.findViewById(R.id.btnBottomMenu);
        btnCloseMenu = (ImageButton) rlMenu.findViewById(R.id.btnCloseMenu);
        txtName = (TextView) v.findViewById(R.id.txtName);
        if (isTopMenuVisible) {
            btnOpenMenu.setVisibility(View.GONE);
            txtName.setText(getString(R.string.strHome));
        } else {
            btnOpenMenu.setVisibility(View.VISIBLE);
            txtName.setText("Hii");
        }
        btnOpenMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
                if (rlMenu.getVisibility() == View.GONE) {
                    rlMenu.startAnimation(slideDown);
                    rlMenu.setVisibility(View.VISIBLE);
                    isTopMenuVisible = true;
                    try {
                        Thread.sleep(500);
                        setActionBar();
                    }catch (InterruptedException e){}
                }
            }
        });
        btnCloseMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
                rlMenu.startAnimation(slideUp);
                isTopMenuVisible = false;
                rlMenu.setVisibility(View.GONE);
                try {
                    Thread.sleep(500);
                    setActionBar();
                }catch (InterruptedException e){}
            }
        });
    }*/
}
