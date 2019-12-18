package com.example.szidonialaszlo.simplezebraproiect;

public interface IOnScannerEvent {

    //Function is called to pass scanned data
    void onDataScanned(String scanData);

    //Function is called to pass scanner status
    void onStatusUpdate(String scanStatus);

    //Function to be called upon error or exception
    void onError();
}
