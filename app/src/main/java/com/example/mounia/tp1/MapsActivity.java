package com.example.mounia.tp1;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Context context ;
    RelativeLayout relativeLayout;
    private WifiManager wifiManager;

    private WifiInfo wifiInfo;

    private String ssid;

    private String bssid;

    private int rssi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
               .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialser le WifiManager
        wifiManager = (WifiManager) getApplicationContext().getSystemService(getApplicationContext().WIFI_SERVICE);

        // Recuperer les infos du Wifi
        wifiInfo = wifiManager.getConnectionInfo();

        // Recuperer le SSID
        ssid = wifiInfo.getSSID();

        // Recuperer le BSSID
        bssid = wifiInfo.getBSSID();

        // Recuperer le RSSI
        rssi = wifiInfo.getRssi();

       // context = getApplicationContext();
        //relativeLayout = (RelativeLayout) findViewById(R.id.relativeLayout);

    }



    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng polytechnique = new LatLng(45.50, -73.61);
        MarkerOptions markerPoly = new MarkerOptions().position(polytechnique).title("Position").snippet("École polytechnique");

        mMap.addMarker(markerPoly);
        TextView textInfo = (TextView)findViewById(R.id.info);
        textInfo.setText(ssid);
       /* mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                //Intent intent1 = new Intent(MapsActivity.this, this.getClass() );
                //intent1.putExtra("markertitle", title);
                //startActivity(intent1);
                //String ssid = wifiInfo.getSSID();
                String ssid = "essai";


            }
        });*/

    }

}
