package com.example.mounia.tp1;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        FragmentListePointsAcces.OnPointAccesSelectedListener, FragmentDetailsPointAcces.OnDetailsInteractionListener
{
    private GoogleMap mMap;
    private WifiManager wifiManager;
    private ArrayList<PointAcces> pointsAcces;
    private FragmentManager fragmentManager;

    private TextView textBattery;

    // Les deux fragments dynamiques qui peuvent se remplacer
    private FragmentListePointsAcces fragmentListePointsAcces;
    private FragmentDetailsPointAcces fragmentDetailsPointAcces;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialiser les fragments dynamiques
        fragmentListePointsAcces = new FragmentListePointsAcces();
        fragmentDetailsPointAcces = new FragmentDetailsPointAcces();

        // Initialiser le fragment manager
        fragmentManager = getSupportFragmentManager();

        // Initialiser le WifiManager
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);

        textBattery = (TextView) findViewById(R.id.textBattery);
        textBattery.setText(obtenirBattery(this));



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
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng polytechnique = new LatLng(45.50, -73.61);
        MarkerOptions markerPoly = new MarkerOptions().position(polytechnique).title("Position").snippet("École polytechnique");

        mMap.addMarker(markerPoly);

        // Quand l'application se lance, les points d'accès à proximité sont détectés
        this.pointsAcces = detecterPointsAcces();

        // Ces points d'accès doivent être placés sur la carte.
        placerMarkersSurCarte();

        // TODO
        // ...
    }

    @Override
    public void onResume(){
        super.onResume();

        // NB: Once the activity reaches the resumed state, you can freely add and remove fragments
        // to the activity. Thus, only while the activity is in the resumed state can the
        // lifecycle of a fragment change independently.
        // However, when the activity leaves the resumed state, the fragment again is pushed
        // through its lifecycle by the activity.

        // Placer dynamiquement le fragment liste de points d'acces
        // pour qu'il puisse aussi etre remplace dynamiquement.
        if (findViewById(R.id.conteneur_fragment_dynamique) != null)
            fragmentManager.beginTransaction().add(R.id.conteneur_fragment_dynamique, fragmentListePointsAcces).commit();
    }

    public void remplacerFragment(Fragment remplacant, Bundle donneesATransmettre, int idConteneurFragment)
    {
        // Passer les arguments au nouveau fragment (remplacant)
        remplacant.setArguments(donneesATransmettre);

        // Remplacer l'ancien fragment par le remplacant
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idConteneurFragment, remplacant);

        // Ajouter la transaction sur la pile de retour pour permettre
        // a l'usager de retourner a l'etat d'avant la transaction.
        // Note : aucun argument n'est a passer ici.
        fragmentTransaction.addToBackStack(null);

        // Tip: For each fragment transaction, you can apply a transition animation,
        // by calling setTransition() before you commit.
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Appliquer les changements
        fragmentTransaction.commit();
    }

    public ArrayList<PointAcces> detecterPointsAcces()
    {
        // TODO : détecter les points d'accès à proximité et les renvoyer
        // ...

        // Pour commencer, faire le test pour une seule détection
        ArrayList<PointAcces> pointAccesDetectes = new ArrayList<>();
        PointAcces pointAcces = new PointAcces(wifiManager.getConnectionInfo());
        pointAccesDetectes.add(pointAcces);

        return pointAccesDetectes;
    }

    public void placerMarkersSurCarte()
    {
        // TODO : placer des markers pour les points d'accès détectés sur la carte
        // ...
    }

    @Override
    public void onPointAccesSelected(int position) {
        // Test
        Toast.makeText(this, "Point Acces " + position + " selectionne", Toast.LENGTH_LONG).show();

        // TODO next
        // ...
        remplacerFragment(this.fragmentDetailsPointAcces, null, R.id.conteneur_fragment_dynamique);
    }

    // Finalement, la fonctionnalité partager sert à envoyer l' information
    // d'un hotspot (SSID,BSSID,etc...) à un de nos contacts téléphoniques ou
    // à un contact d'une autre app ( facebook, whatsapp, etc...).
    @Override
    public void partager(int idPointAcces) {
        // TODO ...

        Intent sharingnIntent = new Intent(Intent.ACTION_SEND);
        sharingnIntent.setType("text/plain");
        String shareBodyText = pointsAcces.get(idPointAcces).obtenirSSID();
        sharingnIntent.putExtra(Intent.EXTRA_SUBJECT,"WIFI Gratuit");
        sharingnIntent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
        startActivity(Intent.createChooser(sharingnIntent,"partager par"));
    }

    @Override
    public void ajouterAuxFavoris(int idPointAcces) {
        // TODO ...
        // @Mounia
    }

    // Juste pour permettre d'enlever, même si ce n'est pas dans l'énoncé
    @Override
    public void enleverDesFavoris(int idPointAcces) {
        // TODO ...
        // @Mounia
    }

    @Override
    public Path.Direction obtenirDirection(int idPointAcces) {
        // TODO ...
        // @Philippe
        return null;
    }


    static String obtenirBattery(Context context) {
            Intent batteryIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            if(batteryIntent != null) {
                int level = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
                int scale = batteryIntent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

                if (level > -1 && scale > 0) {
                    return Float.toString(((float) level / (float) scale) * 100.0f);
                }
            }

        return null;
    }

}
