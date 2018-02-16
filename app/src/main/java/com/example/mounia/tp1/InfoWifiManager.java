package com.example.mounia.tp1;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

/**
 * Created by mounianordine on 18-02-14.
 */

public class InfoWifiManager {

    private WifiManager wifiManager;
    private WifiInfo wifiInfo;
    private String ssid;
    private String bssid;
    private int rssi;

    public InfoWifiManager()
    {}

    public InfoWifiManager(Context context)
    {
        // Initialser le WifiManager
        wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

        // Recuperer les infos du Wifi
        wifiInfo = wifiManager.getConnectionInfo();

        // Recuperer le SSID
        ssid = wifiInfo.getSSID();

        // Recuperer le BSSID
        bssid = wifiInfo.getBSSID();

        // Recuperer le RSSI
        rssi = wifiInfo.getRssi();
    }


    public String afficher()
    {
        String info = "SSID: " + ssid + " BSSID: " + bssid;
        return info;
    }


}
