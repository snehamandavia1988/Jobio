package com.lab360io.jobio.fieldapp;

import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.xwray.fontbinding.FontCache;

import io.fabric.sdk.android.Fabric;
import me.zhanghai.android.materialedittext.MaterialEditText;
import utility.MyFunction;


public class acManualQRCode extends AppCompatActivity {
    Button btnOk, btnCancel;
    MaterialEditText edQRCode;
    AppCompatActivity ac;
    Handler handler = new Handler();
    MyFunction objMyFunction = new MyFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        DataBindingUtil.setContentView(this, R.layout.manual_qr_code);
        ac = this;
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.strCompany));
        ab.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.tilt)));

        edQRCode = (MaterialEditText) findViewById(R.id.edQRCode);
        btnOk = (Button) findViewById(R.id.btnOK);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyFunction.isFieldBlank(edQRCode.getText().toString())) {
                    edQRCode.setError(getString(R.string.strEnterQRCode));
                    requestFocus(edQRCode);
                } else {
                    objMyFunction.verifyingQRcode(ac, edQRCode, handler, edQRCode.getText().toString());
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objMyFunction.pdVerifyQrCode != null && objMyFunction.pdVerifyQrCode.isShowing())
            objMyFunction.pdVerifyQrCode.dismiss();
    }
}
