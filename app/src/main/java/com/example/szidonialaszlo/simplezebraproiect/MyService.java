package com.example.szidonialaszlo.simplezebraproiect;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;

import com.symbol.emdk.EMDKManager;
import com.symbol.emdk.EMDKResults;
import com.symbol.emdk.simulscan.SimulScanConfig;
import com.symbol.emdk.simulscan.SimulScanData;
import com.symbol.emdk.simulscan.SimulScanException;
import com.symbol.emdk.simulscan.SimulScanManager;
import com.symbol.emdk.simulscan.SimulScanMultiTemplate;
import com.symbol.emdk.simulscan.SimulScanReader;
import com.symbol.emdk.simulscan.SimulScanReaderInfo;
import com.symbol.emdk.simulscan.SimulScanStatusData;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MyService extends Service implements EMDKManager.EMDKListener, SimulScanReader.StatusListerner, SimulScanReader.DataListerner {


    private EMDKResults results;
    private EMDKManager emdkManager = null;
    private SimulScanManager simulscanManager = null;
    private List<SimulScanReaderInfo> readerInfoList = null;
    private SimulScanReader selectedSimulScanReader = null;
    private final Random mGenerator = new Random();
    Context context = this.getBaseContext();

    public MyService() {
        selectEMDK();

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);   String hallo = "hal";   }


    public String selectEMDK() {
        results = EMDKManager.getEMDKManager(context, this);
        if (results.statusCode != EMDKResults.STATUS_CODE.SUCCESS) {
            return "Es geht nicht";   }
        return "ES geht";
    }


    @Override
    public void onOpened(EMDKManager emdkManager) {

        this.emdkManager = emdkManager;
        simulscanManager = (SimulScanManager) emdkManager.getInstance(EMDKManager.FEATURE_TYPE.SIMULSCAN);
        if (null == simulscanManager) {
            return;   }
        readerInfoList = simulscanManager.getSupportedDevicesInfo();

        List<String> nameList = new ArrayList<>();

        for (SimulScanReaderInfo readerInfo : readerInfoList) {
            nameList.add(readerInfo.getFriendlyName());   }
    }

    @Override
    public void onClosed() {

    }

    @Override
    public void onData(SimulScanData simulScanData) {

    }

    @Override
    public void onStatus(SimulScanStatusData simulScanStatusData) {

    }

    private void onStart() {
        if (selectedSimulScanReader != null) {

            try {
                if (!selectedSimulScanReader.isEnabled()) {
                    selectedSimulScanReader.enable();   }

            } catch (SimulScanException e)
            {   e.printStackTrace();   }   }
    }

    private void readCurrentScanner() throws Exception {
        setCurrentConfig();
        if (selectedSimulScanReader != null) {
            selectedSimulScanReader.read();
        }
    }

    private void setCurrentConfig() throws Exception {
        if (selectedSimulScanReader != null) {
            SimulScanConfig config = selectedSimulScanReader.getConfig();
            if (config != null) {
                File file = new File("/sdcard/simulscan/templates/ups.xml");
                try {
                    SimulScanMultiTemplate myTemplate = new SimulScanMultiTemplate(simulscanManager, Uri.fromFile(file));
                    if (myTemplate != null) {
                        config.multiTemplate = myTemplate;
                        config.identificationTimeout = 15000;
                        config.processingTimeout = 10000;
                        config.userConfirmationOnScan = true;
                        config.autoCapture = true;
                        config.debugMode = false;
                        config.audioFeedback = true;
                        config.hapticFeedback = true;
                        config.ledFeedback = true;
                        selectedSimulScanReader.setConfig(config);
                    }
                } catch (Exception e) {
                    e.printStackTrace();   }
            }
        }
    }

    private void initCurrentSCanner() throws SimulScanException {
        selectedSimulScanReader.addStatusListener(this);
        selectedSimulScanReader.addDataListener(this);
        selectedSimulScanReader.enable();
    }


    private void deinitCurentScanner() throws SimulScanException {
        if (selectedSimulScanReader != null) {
            if (selectedSimulScanReader.isReadPending())
                selectedSimulScanReader.cancelRead();
            if (selectedSimulScanReader.isEnabled())
                selectedSimulScanReader.disable();
            selectedSimulScanReader.removeDataListener(this);
            selectedSimulScanReader.removeStatusListener(this);
            selectedSimulScanReader = null;   }   }
}


