package com.lab360io.jobio.fieldapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.xwray.fontbinding.FontCache;

import entity.BusinessAccountMaster;
import io.fabric.sdk.android.Fabric;
import utility.ConstantVal;
import utility.Logger;
import utility.MyFunction;


public class acSplash extends Activity {
    ImageView imgLogo;
    TextView txtAccountName;
    Context mContext;
    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        DataBindingUtil.setContentView(this, R.layout.splash);
        mContext = this;
        imgLogo = (ImageView) findViewById(R.id.imgLogo);
        txtAccountName = (TextView) findViewById(R.id.txtAccountName);
        final boolean isConfigure = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_QRCODE_CONFIGURE, false);
        Thread t1 = new Thread() {
            public void run() {
                if (!isConfigure) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            imgLogo.setVisibility(View.VISIBLE);
                            txtAccountName.setVisibility(View.GONE);
                            imgLogo.setImageResource(R.drawable.ic_spash_logo);
                        }
                    });
                } else {
                    //get the image value from server
                    final String strBase64 = MyFunction.getStringPreference(mContext, BusinessAccountMaster.Fields.ACCOUNT_LOGO, "");
                    final String imageDataBytes = strBase64.substring(strBase64.indexOf(",") + 1);
                    Logger.debug(imageDataBytes);
                    final String strAccountName = MyFunction.getStringPreference(mContext, BusinessAccountMaster.Fields.ACCOUNT_NAME, "");
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (!strBase64.equals("")) {
                                try {
                                    imgLogo.setVisibility(View.VISIBLE);
                                    txtAccountName.setVisibility(View.GONE);
                                    imgLogo.setImageBitmap(MyFunction.convertBase64ImageToBitmap(imageDataBytes));
                                } catch (Exception e) {
                                    Logger.debug(e.getMessage());
                                    txtAccountName.setVisibility(View.VISIBLE);
                                    imgLogo.setVisibility(View.GONE);
                                    txtAccountName.setText(strAccountName);
                                }
                            } else {
                                txtAccountName.setVisibility(View.VISIBLE);
                                imgLogo.setVisibility(View.GONE);
                                txtAccountName.setText(strAccountName);
                            }
                        }
                    });
                }
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                if (!isConfigure) {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(mContext, acQRCodeScanner.class);
                            startActivity(i);
                            finish();
                        }
                    });
                } else {
                    final int tokenId = MyFunction.getIntPreference(mContext, ConstantVal.TOKEN_ID, 0);
                    boolean isSessionExists = MyFunction.getBooleanPreference(mContext, ConstantVal.IS_SESSION_EXISTS, false);
                    Logger.debug("tokenId:" + tokenId + " isSessionExists:" + isSessionExists);
                    if (tokenId == 0 && !isSessionExists) {//User never logged in
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Intent i = new Intent(mContext, acLogin.class);
                        startActivity(i);
                        finish();
                    } else if (tokenId != 0) {//User logged in atleast one time.
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (isSessionExists)
                            MyFunction.startBackgroundService(mContext);
                        Intent i = new Intent(mContext, acHome.class);
                        startActivity(i);
                        finish();
                    }
                }
            }
        };

        t1.start();
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.start();
    }

    
}

