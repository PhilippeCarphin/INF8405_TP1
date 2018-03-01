package com.example.mounia.tp1;

import android.graphics.Path;
import android.net.wifi.ScanResult;
import android.content.BroadcastReceiver;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.Toast;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        FragmentListePointsAcces.OnPointAccesSelectedListener, FragmentDetailsPointAcces.OnDetailsInteractionListener
{
    private BroadcastReceiver wifiScanReceiver;
    private List<ScanResult> scanResults;

    private GoogleMap mMap;
    private WifiManager wifiManager;
    private ArrayList<PointAcces> pointsAcces;
    private ArrayList<PointAcces> favoris;      // Liste de points d'acces favoris
    private FragmentManager fragmentManager;

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
        fragmentListePointsAcces  = new FragmentListePointsAcces();
        fragmentDetailsPointAcces = new FragmentDetailsPointAcces();

        // Initialiser le fragment manager
        fragmentManager = getSupportFragmentManager();

        // Initialiser le WifiManager
        //scanResults = new ArrayList<ScanResult>();
        scanResults = null; // juste pour voir
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);
        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
                if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                    scanResults = wifiManager.getScanResults();
                }
            }
        };

        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        //wifiManager.startScan();

        // Quand l'application se lance, les points d'accès à proximité sont détectés
        this.pointsAcces = detecterPointsAcces();

        // Après avoir scanné, passer la liste de points d'accès proches détectés
        // au fragment liste de points accès.
        fragmentListePointsAcces.assignerPointsAccesDetectes(pointsAcces);
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

        // Ces points d'accès doivent être placés sur la carte.
        placerMarkersSurCarte();

        mMap.setOnMarkerClickListener(this);

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
        // Prochain push j'essaie de tenir compte de ta remarque @Philippe
        fragmentTransaction.addToBackStack(null);

        // Tip: For each fragment transaction, you can apply a transition animation,
        // by calling setTransition() before you commit.
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        // Appliquer les changements
        fragmentTransaction.commit();
    }

    public ArrayList<PointAcces> detecterPointsAcces()
    {
        // Détecter les points d'accès à proximité
        wifiManager.startScan();

        // Recuperer la liste de points d'acces identifies
        scanResults = wifiManager.getScanResults();

        // Ajouter
        ArrayList<PointAcces> pointAccesDetectes = new ArrayList<>(10);
        for(ScanResult scanResult : scanResults)
            pointAccesDetectes.add(new PointAcces(scanResult));

        // Juste pour tester
        for(PointAcces pa : pointAccesDetectes) {
            Log.i("wifi", pa.toString());
        }

        return pointAccesDetectes;
    }

    static private final Random rand = new Random();
    private MarkerOptions pointAccesToMarkerOptions(PointAcces pa){
        MarkerOptions mo;

        double distance = 25;
        double dx = distance * rand.nextDouble();
        double dy = distance * rand.nextDouble();

        LatLng coords = new LatLng(45.5017 + dx, -73.5673 + dy);

        mo = new MarkerOptions()
                .position(coords)
                .title("Wifi Hotspot " + pa.toString());

        return mo;
    }

    public boolean onMarkerClick(Marker marker){
        int id;
        try {
            id = (Integer) marker.getTag();
        } catch (NullPointerException e) {
            Log.i("NULLPTR", "Getting integer is fucked up");
            id = 0;
        }
        Log.i("MAP MARKER", "MARKER CLICKED : Number " + String.valueOf(id));
        onPointAccesSelected(id);
        return false;
    }

    public void placerMarkersSurCarte()
    {
        // TODO : placer des markers pour les points d'accès détectés sur la carte
        // ...
        for(PointAcces pa : pointsAcces){
            MarkerOptions mo = pointAccesToMarkerOptions(pa);

            Marker m = mMap.addMarker(mo);
            m.setTag(Integer.valueOf(pa.obtenirID()));
        }
    }

    // Permet de chercher un point d'acces dans un array list.
    // Pour le moment, on a juste ce besoin.
    // Si aucun point d'acces avec idPointAcces n'est trouve dans pointsAcces
    // alors cette fonction renvoie null
    public static PointAcces trouverPointAcces(ArrayList<PointAcces> pointsAcces, int idPointAcces) {
        for (PointAcces pointAcces : pointsAcces)
            if (pointAcces.obtenirID() == idPointAcces)
                return pointAcces;
        return null;
    }

    @Override
    public void onPointAccesSelected(int idPointAcces) {

        // Recuperer le point d'acces selectionne
        PointAcces pointAcces = trouverPointAcces(pointsAcces, idPointAcces);

        // Test
//        Log.i("PointAcces", "onPointAccesSelected() position :" + idPointAcces);
//        Log.i("PointAcces", pointsAcces.get(idPointAcces).toString());
//        Log.i("PointAcces", "id :" + pointsAcces.get(idPointAcces).obtenirID());

        fragmentDetailsPointAcces = new FragmentDetailsPointAcces();

        // Assigner le point d'acces selectionne au fragment de details
        fragmentDetailsPointAcces.assignerPointAcces(pointAcces);

        // Remplacer le fragment de liste de points d'acces par celui des infos du point d'acces selectionne
        remplacerFragment(this.fragmentDetailsPointAcces, null, R.id.conteneur_fragment_dynamique);

        // Mettre les vues a jour pour ce nouveau point d'acces
        // fragmentDetailsPointAcces.mettreVuesAJour();
    }

    // Finalement, la fonctionnalité partager sert à envoyer l' information
    // d'un hotspot (SSID,BSSID,etc...) à un de nos contacts téléphoniques ou
    // à un contact d'une autre app ( facebook, whatsapp, etc...).
    @Override
    public void partager(int idPointAcces) {
        // TODO ...
        // Déjà réalisé par Mounia...

        // Test
        Toast.makeText(this, "Partager point acces : " + idPointAcces, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void ajouterAuxFavoris(int idPointAcces) {
        // Test, enlever le Toast suivant une fois que cette fonction peut etre convoquee
        Toast.makeText(this, "Ajouter point acces : " + idPointAcces, Toast.LENGTH_SHORT).show();

        // TODO : Chercher dans la liste pointsAcces le point d'acces ayant l'id
        // ...

        // TODO : Une fois trouve, mettre l'attribut estFavori de ce point d'acces a vrai
        // en appelant sa methode ajouterAuxFavoris
        // ...

        // TODO : Ajouter ce point d'acces dans la liste de favoris
        // ...

        // TODO : Ajouter ce point d'acces egalement dans les SharedPreferences ou une
        // table SQLite de points d'acces favoris ou une autre methode de persistence
        // ...
    }

    // Juste pour permettre d'enlever, même si ce n'est pas dans l'énoncé
    @Override
    public void enleverDesFavoris(int idPointAcces) {
        // Test
        Toast.makeText(this, "Enlever point acces : " + idPointAcces, Toast.LENGTH_SHORT).show();

        // TODO : Faire les operations inverses qui se trouvent dans la fonction ajouterAuxFavoris
        // ...
    }

    @Override
    public Path.Direction obtenirDirection(int idPointAcces) {
        // TODO ...
        // @Philippe
        return null;
    }
}
