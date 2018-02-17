package com.example.mounia.navigationdrawer;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by mounianordine on 18-02-16.
 */

public class MapFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap mgoogleMap;
    View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
         mView = inflater.inflate(R.layout.content_main, container, false);

        return mView;
    }
@Override
public void onViewCreated(View view, Bundle saveInstanceState)
{
    super.onViewCreated(view,saveInstanceState);
    mMapView = (MapView) mView.findViewById(R.id.map);
    if(mMapView != null)
    {
        mMapView.onCreate(null);
        mMapView.onResume();
        mMapView.getMapAsync(this);
    }

}


    @Override
    public void onMapReady(GoogleMap googleMap) {

       MapsInitializer.initialize(getActivity());
       mgoogleMap= googleMap;
       googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        LatLng sydney = new LatLng(-34, 151);
        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"));


    }
}
