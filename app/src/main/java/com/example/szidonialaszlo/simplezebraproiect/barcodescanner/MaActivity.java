package com.example.szidonialaszlo.simplezebraproiect.barcodescanner;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.szidonialaszlo.simplezebraproiect.R;

public class MaActivity extends AppCompatActivity implements View.OnClickListener {

    // Create tag for logging purposes
    private final static String TAG = "[Z] MainActivity - ";
    private final static Button btn[] = new Button[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma);
        System.out.println (TAG + "onCreate() ");
        try {
            String emdkPkgName = "com.symbol.emdk.emdkservice";
            PackageInfo packageinfo = getPackageManager().getPackageInfo(emdkPkgName, 0);
            String emdkVersion = packageinfo.versionName;
            // Toast to display verions of EMDK service running on device
            Toast.makeText(MaActivity.this, "EMDK version on this device is: " + emdkVersion,
                    Toast.LENGTH_LONG).show();
        } catch (PackageManager.NameNotFoundException e) {
            // EMDK service is not installed on the device.
            System.out.println (TAG + "onCreate() - NameNotFoundException " + e.getMessage());
            e.printStackTrace();
        }
        btn[0] = (Button) findViewById(R.id.button1);
        btn[1] = (Button) findViewById(R.id.button2);
        for(int i=0; i<2; i++){
            btn[i].setOnClickListener(this);
        }
    }

    public void onClick(View v) {
        if(v == findViewById(R.id.button1)){
            Intent intent = new Intent(this, Main1Activity.class);
            startActivity(intent);
        }
        else if(v == findViewById(R.id.button2)){
            Intent intent = new Intent(this, Main2Activity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
