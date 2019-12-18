package com.example.szidonialaszlo.simplezebraproiect.barcodescanner;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.szidonialaszlo.simplezebraproiect.R;

public class Main1Activity extends AppCompatActivity implements IOnScannerEvent {

    // Create tag for logging purposes
    private final static String TAG = "[Z] Main1Activity - ";
    // Text view to display status of EMDK and Barcode Scanning operations
    private TextView StatusView = null;
    // Text view to display scanned barcode data
    private TextView DataView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        // Reference to UI elements declared in the screen layout
        StatusView = (TextView) findViewById(R.id.StatusView);
        DataView = (TextView) findViewById(R.id.DataView);

    }

    @Override
    protected void onResume(){
        super.onResume();
        BarcodeScanner.getInstance(this);
        BarcodeScanner.registerUIobject(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BarcodeScanner.unregisterUIobject();
    }

    @Override
    public void onDataScanned(String scanData) {
        DataView.append(scanData + "\n");
    }

    @Override
    public void onStatusUpdate(String scanStatus) {
        StatusView.setText(scanStatus);
    }

    @Override
    public void onError() {
        System.out.println (TAG + "onError() ");
    }

    @Override
    public void onStop() {
        super.onStop();
        BarcodeScanner.deInitScanner();
        //resuming from a screen suspend event the onDestroy method doesn't trigger, therefore releasing EMDK here
        BarcodeScanner.releaseEmdk();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Given EMDK is already released from the onStop method, this isn't necessary
        BarcodeScanner.releaseEmdk();
    }
}


