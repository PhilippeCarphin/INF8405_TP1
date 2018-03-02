package com.example.mounia.tp1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Path;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Cette classe est l'activité principale pour le projet.
 *
 * @author Reph Dauphin Mombrun, Mounia Nordine, Philippe Carphin
 */
public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener,
        FragmentListePointsAcces.OnPointAccesSelectedListener, FragmentDetailsPointAcces.OnDetailsInteractionListener
{
    private BroadcastReceiver wifiScanReceiver;
    private List<ScanResult> scanResults;

    private GoogleMap mMap;
    static private LatLng LAT_LNG_POLY = new LatLng(45.50, -73.61);
    private WifiManager wifiManager;
    private ArrayList<PointAcces> pointsAcces;
    private ArrayList<PointAcces> favoris;      // Liste de points d'acces favoris
    private FragmentManager fragmentManager;

    private TextView textBattery;

    // Les deux fragments dynamiques qui peuvent se remplacer
    private FragmentListePointsAcces fragmentListePointsAcces;
    private FragmentDetailsPointAcces fragmentDetailsPointAcces;
    private FragmentFavoris fragmentFavoris;

    // sharedPreference
    private SharedPreference sharedPreference;
    private Gson gson;

    // Permet de savoir si
    private boolean fragmentDetailsDejaEmpile = false;


    /**
     * Cette méthode est appelée lors de la création de l'activité
     * @param savedInstanceState
     */
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
        fragmentFavoris = new FragmentFavoris();


        // Initialiser le fragment manager
        fragmentManager = getSupportFragmentManager();

        // Initialiser le WifiManager
        scanResults = null;
        wifiManager = (WifiManager) this.getApplicationContext().getSystemService(WIFI_SERVICE);

        textBattery = findViewById(R.id.textBattery);
        textBattery.setText(obtenirBattery(this));

        wifiScanReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context c, Intent intent) {
            if (intent.getAction().equals(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)) {
                scanResults = wifiManager.getScanResults();
            }
            }
        };

        registerReceiver(wifiScanReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

        // Quand l'application se lance, les points d'accès à proximité sont détectés
        this.pointsAcces = detecterPointsAcces();

        // Après avoir scanné, passer la liste de points d'accès proches détectés
        // au fragment liste de points accès.
        fragmentListePointsAcces.assignerPointsAccesDetectes(pointsAcces);

        // placerMarkersSurCarte();
        favoris = new ArrayList<PointAcces>();

        // Initialiser des objets permenttant la sauvegarde des favoris
        gson = new Gson();
        sharedPreference = new SharedPreference(getApplicationContext());
    }

    /**
     * Manipule la map lorsque celle-ci devient disponible.
     * Ce callback est appelé lorsque la map est prête à être utilisée.
     * C'est ici que les marqueurs sont ajoutés.
     */
    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;



        placerMarqueurPoly();

        // Ces points d'accès doivent être placés sur la carte.
        placerMarkersSurCarte();

        mMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(LAT_LNG_POLY));
        mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
    }

    private void placerMarqueurPoly() {
        MarkerOptions markerPoly = new MarkerOptions().position(LAT_LNG_POLY).title("Position").snippet("École polytechnique");
        markerPoly.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
        mMap.addMarker(markerPoly);
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

    /**
     * Cette fonction encapsule la transaction de remplacement du fragment qui se trouve du côté
     * droit de l'activité.
     * @param remplacant
     * @param donneesATransmettre
     * @param idConteneurFragment
     */
    public void remplacerFragment(Fragment remplacant, Bundle donneesATransmettre, int idConteneurFragment,
                                  boolean addToBackStack)
    {
        // Passer les arguments au nouveau fragment (remplacant)
        remplacant.setArguments(donneesATransmettre);

        // Remplacer l'ancien fragment par le remplacant
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(idConteneurFragment, remplacant);

        // Ajouter la transaction sur la pile de retour pour permettre
        // a l'usager de retourner a l'etat d'avant la transaction.
        // Prochain push j'essaie de tenir compte de ta remarque @Philippe
        if (addToBackStack)
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
            pointAccesDetectes.add(PointAcces.PointAccesFromScanResult(scanResult));

        if(scanResults.size() == 0){
            pointAccesDetectes = noScanResultsFallback();
        }
        // Juste pour tester
        for(PointAcces pa : pointAccesDetectes) {
            Log.i("wifi", pa.toString());
        }

        return pointAccesDetectes;
    }

    public ArrayList<PointAcces> noScanResultsFallback(){
        ArrayList<PointAcces> fallback = new ArrayList<>(10);
        fallback.add(new PointAcces("It Hurts when IP", "00-14-22-01-23-45", 2));
        fallback.add(new PointAcces("PolyFab", "00-99-22-01-23-45", 2));
        fallback.add(new PointAcces("Sur le pont d'Avignon", "00-14-22-01-23-45", 2));
        fallback.add(new PointAcces("I'm pretty fly for a WiFi", "00-14-22-01-23-45", 2));
        fallback.add(new PointAcces("BELL451", "00-14-22-01-23-45", 2));
        return fallback;
    }

    static private final Random rand = new Random();

    /**
     * Crée une instance de MarkerOptions à partir d'un PointAcces en vue de le mettre sur la map.
     * La fonction lui donne des coordonnées distribuées aléatoirement autour de Poly
     * @param pa Le point d'accès d'entrée
     * @return un MarkerOptions avec des coordonnées pas trop loin de Poly
     */
    private MarkerOptions pointAccesToMarkerOptions(PointAcces pa){
        MarkerOptions mo;

        double distance = 10;
        double dx = distance * (rand.nextDouble() - 0.5);
        double dy = distance * (rand.nextDouble() - 0.5);

        LatLng coords = new LatLng(LAT_LNG_POLY.latitude + dx, LAT_LNG_POLY.longitude + dy);

        mo = new MarkerOptions()
                .position(coords)
                .title("Wifi Hotspot " + pa.toString());

        return mo;
    }

    /**
     * Fonction de rappel pour le click d'un marqueur.  On extrait le ID du point d'accès et on
     * signale la sélection du point d'accès en passant le ID à onPointAccesSelected().
     * @param marker le marqueur cliqué
     * @return booléen spécifiant si oui ou non on veut bloquer le comportement par défaut.
     * Ce comportement n'est pas bloqué.
     */
    public boolean onMarkerClick(Marker marker){
        int id = 0;
        try {
            id = (Integer) marker.getTag();
            onPointAccesSelected(id);
        } catch (NullPointerException e) {
            // Si le marqueur n'a pas d'ID, c'est que c'est le marqueur de l'emplacement de POLY
            // On ne fait pas d'onPointAccesSelected.
        }
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

    /**
     * Permet de chercher un point d'acces dans un array list.
     * @param pointsAcces la liste dans laquelle chercher
     * @param idPointAcces le id à rechercher.
     * @return Le PointAcces ou null si pas trouvé
     */
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

        fragmentDetailsPointAcces = new FragmentDetailsPointAcces();

        // Assigner le point d'acces selectionne au fragment de details
        fragmentDetailsPointAcces.assignerPointAcces(pointAcces);

        boolean addToBackStack = !(fragmentManager.getPrimaryNavigationFragment() instanceof FragmentDetailsPointAcces);

        // Remplacer le fragment de liste de points d'acces par celui des infos du point d'acces selectionne
        remplacerFragment(this.fragmentDetailsPointAcces, null,
                R.id.conteneur_fragment_dynamique, true);
    }

    /**
     * Finalement, la fonctionnalité partager sert à envoyer l' information
     * d'un hotspot (SSID,BSSID,etc...) à un de nos contacts téléphoniques ou
     * à un contact d'une autre app ( facebook, whatsapp, etc...).
     * @param idPointAcces
     */
    @Override
    public void partager(int idPointAcces) {
        // FIXME : partager aussi le BSSID et autres infos pertinentes...
        Intent sharingnIntent = new Intent(Intent.ACTION_SEND);
        sharingnIntent.setType("text/plain");
        String shareBodyText = pointsAcces.get(idPointAcces).obtenirSSID();
        sharingnIntent.putExtra(Intent.EXTRA_SUBJECT,"WIFI Gratuit");
        sharingnIntent.putExtra(Intent.EXTRA_TEXT,shareBodyText);
        startActivity(Intent.createChooser(sharingnIntent,"partager par"));
    }

    /**
     * Ajoute le point d'accès aux favoris
     * @param idPointAcces
     */
    @Override
    public void ajouterAuxFavoris(int idPointAcces) {

        // Chercher dans la liste pointsAcces le point d'acces ayant l'id
        for(int i = 0; i< pointsAcces.size(); i++) {
            if (pointsAcces.get(i).obtenirID() == idPointAcces) {

                // Une fois trouve, mettre l'attribut estFavori de ce point d'acces a vrai
                pointsAcces.get(i).ajouterAuxFavoris();

                // Ajouter ce point d'acces dans la liste de favoris
                favoris.add(pointsAcces.get(i));

                // Ajouter ce point d'acces egalement dans les SharedPreferences
                String jsonScore = gson.toJson(favoris);
                sharedPreference.saveList(jsonScore);
            }
        }
    }


    /**
     * Obtient un fragment FragmentListPointsAcces à partir des shared preferences
     */
    public ArrayList<PointAcces> obtenirListFromSharedPreference() {
        String jsonScore = sharedPreference.getList();
        Type type = new TypeToken<List<PointAcces>>(){}.getType();
        return gson.fromJson(jsonScore, type);
    }

    /**
     * Juste pour permettre d'enlever, même si ce n'est pas dans l'énoncé
     * @param idPointAcces
     */
//    @Override
//    public void enleverDesFavoris(int idPointAcces) {
//        // Test
//        Toast.makeText(this, "Enlever point acces : " + idPointAcces, Toast.LENGTH_SHORT).show();
//
//        // TODO : Faire les operations inverses qui se trouvent dans la fonction ajouterAuxFavoris
//        for(int i = 0; i< pointsAcces.size(); i++) {
//            if (pointsAcces.get(i).obtenirID() == idPointAcces) {
//
//                pointsAcces.get(i).enleverDesFavoris();
//
//                favoris.remove(pointsAcces.get(i));
//
//                // TODO : mettre à jour la liste dans les SharedPreferences
//                String jsonScore = gson.toJson(pointsAcces);
//                sharedPreference.saveList(jsonScore);
//
//            }
//        }
//    }

    /**
     * TODO Doit trouver le chemin entre notre emplacement et l'emplacement du marqueur cliqué ou
     * le marqueur dont on a demandé les directions avec le bouton get-directions.
     * @param idPointAcces
     * @return
     */
    @Override
    public Path.Direction obtenirDirection(int idPointAcces) {
        // TODO ...
        // @Philippe
        return null;
    }

    /**
     * Retourne le niveau de la batterie en chaine de caractère.
     * @param context
     * @return
     */
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

    // Permet de traquer les fragments empiles sur le backstack
    //private List<Fragment> fragmentsEmpiles = null;

    public void onFavorisClick(View view)
    {
        if (favoris == null)
            throw new NullPointerException("favoris is null");

        if (obtenirListFromSharedPreference() == null)
            throw new NullPointerException("obtenirListFromSharedPreference returns null");

        fragmentFavoris = new FragmentFavoris();

        // Assigner le point d'acces selectionne au fragment de details
        fragmentFavoris.assignerPointsAccesFavoris(obtenirListFromSharedPreference());

//        boolean addToBackStack = true;
//        for (Fragment fragmentEmpile : fragmentsEmpiles) {
//            if (fragmentEmpile instanceof FragmentFavoris) {
//                addToBackStack = false;
//                break;
//            }
//        }

        boolean addToBackStack = !(fragmentManager.getPrimaryNavigationFragment() instanceof FragmentFavoris);

        // Remplacer le fragment de liste de points d'acces par celui des infos du point d'acces selectionne
        remplacerFragment(this.fragmentFavoris, null,
                R.id.conteneur_fragment_dynamique, true);
    }

}
