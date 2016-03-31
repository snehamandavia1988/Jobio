package com.lab360io.jobio.fieldapp;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.crashlytics.android.Crashlytics;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;
import com.xwray.fontbinding.FontCache;

import io.fabric.sdk.android.Fabric;
import utility.MyFunction;


public class acQRCodeScanner extends AppCompatActivity implements QRCodeReaderView.OnQRCodeReadListener {
    private QRCodeReaderView mydecoderview;
    Button btnEnterManualCode;
    Handler handler = new Handler();
    AppCompatActivity ac;
    MyFunction objMyFunction = new MyFunction();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        FontCache.getInstance(getApplicationContext()).addFont("Ubuntu", "Ubuntu-C.ttf");
        DataBindingUtil.setContentView(this, R.layout.qrcode_scanner);
        ac = this;
        android.support.v7.app.ActionBar ab = getSupportActionBar();
        ab.setTitle(getString(R.string.strCompany));
        ab.setBackgroundDrawable(new ColorDrawable(getResources()
                .getColor(R.color.tilt)));

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mydecoderview.setOnQRCodeReadListener(this);

        btnEnterManualCode = (Button) findViewById(R.id.btnEnterManuallyQRCode);
        btnEnterManualCode.setPaintFlags(btnEnterManualCode.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnEnterManualCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), acManualQRCode.class);
                startActivityForResult(i, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 100) {
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            mydecoderview.getCameraManager().startPreview();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }

    @Override
    public void onQRCodeRead(String text, PointF[] points) {
        String strQRCode = text;
        objMyFunction.verifyingQRcode(ac, mydecoderview, handler, strQRCode);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (objMyFunction.pdVerifyQrCode != null && objMyFunction.pdVerifyQrCode.isShowing())
            objMyFunction.pdVerifyQrCode.dismiss();
    }

    @Override
    public void cameraNotFound() {

    }

    @Override
    public void QRCodeNotFoundOnCamImage() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;
    }
}
