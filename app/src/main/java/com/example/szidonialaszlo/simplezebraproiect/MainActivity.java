package com.example.szidonialaszlo.simplezebraproiect;

import android.app.Activity;
import android.os.Bundle;
import android.renderscript.ScriptC;
import android.widget.EditText;
import android.widget.Toast;

import com.example.szidonialaszlo.simplezebraproiect.barcodescanner.BarcodeScanner;


public class MainActivity extends Activity implements IOnScannerEvent{


    EditText labCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        labCode = (EditText)findViewById(R.id.actcheck_laborCode);


    }

    @Override
    public void onDataScanned(String scanData) {
       // labCode.setText(scanData+"\n");
        labCode.append(scanData+"\n");
        System.out.println(" \n DATA =====> "+scanData);
    }

    @Override
    public void onStatusUpdate(String scanStatus) {
        Toast.makeText(getApplicationContext(),scanStatus,Toast.LENGTH_SHORT).show();
        System.out.println(" \n STATUS =====> "+scanStatus);
    }

    @Override
    public void onError() {
        Toast.makeText(getApplicationContext(),"ERROR", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onResume(){
        super.onResume();
        ScanBarcode.getInstance(this);
        ScanBarcode.registerUIobject(this);

    }

    @Override
    public void onPause() {
        super.onPause();
        ScanBarcode.unregisterUIobject();
    }

    public void onStop() {
        super.onStop();
        ScanBarcode.deInitScanner();
        //resuming from a screen suspend event the onDestroy method doesn't trigger, therefore releasing EMDK here
        ScanBarcode.releaseEmdk();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Given EMDK is already released from the onStop method, this isn't necessary
        ScanBarcode.releaseEmdk();
    }


}





