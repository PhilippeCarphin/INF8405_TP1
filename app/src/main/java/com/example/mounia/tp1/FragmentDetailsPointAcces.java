package com.example.mounia.tp1;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetailsInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentDetailsPointAcces extends Fragment {

    // Pour pointer vers une implementation de cette interface observatrice
    private OnDetailsInteractionListener mListener;

    // Pour pointer vers l'activite parent qui contiendra ce fragment
    private Activity activity = null;

    // Le point d'acces pour lequel ces details  seront affiches
    private PointAcces pointAcces = null;

    // Vues pour le SSID, BSSID et RSSI
    private TextView vueSSID  = null;
    private TextView vueBSSID = null;
    private TextView vueRSSI  = null;

    // Vue pour afficher si c'est avec ou sans mot de passe
    private TextView vueAcces = null;

    // Les vues pour ajouter aux favoris, partager et obenir la direction
    private Button vueAjouterAuxFavoris = null;
    private Button vuePartager          = null;
    private Button vueObtenirDirection  = null;

    // TODO : Ajouter des vues pour le mécanisme d’authentification, de la	gestion	des	clés et	du
    // schéma de chiffrement pris en charge	par	le point d’accès (WEP, WPA etc…).
    // ...

    public FragmentDetailsPointAcces() {
        // Required empty public constructor
    }

    // Doit etre appelee par MapsActivity pour initialiser
    // le fragment avec les bonnes infos du point d'acces detecte
    public void assignerPointAcces(PointAcces pointAcces) {
        this.pointAcces = pointAcces;
    }

    public PointAcces obtenirPointAcces() { return pointAcces; }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity)
            activity = (Activity) context;

        if (context instanceof OnDetailsInteractionListener) {
            mListener = (OnDetailsInteractionListener) activity;
        } else {
            throw new RuntimeException(activity.toString()
                    + " must implement OnDetailsInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // La vue a renvoyer par cette methode
        // RelativeLayout vuePrincipaleFragment = new RelativeLayout(activity);


        // J'ai changé pour linearLayout juste pour voir les infos pour gosser.  Creer trois vues
        LinearLayout vuePrincipaleFragment = new LinearLayout(activity);
        vuePrincipaleFragment.setOrientation(LinearLayout.VERTICAL);
        vuePrincipaleFragment.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
        vuePrincipaleFragment.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
        );

        LinearLayout vueInfo = new LinearLayout(activity);
        vueInfo.setBackgroundColor(0xffaabbcc);
        vueInfo.setOrientation(LinearLayout.VERTICAL);
        vueInfo.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        vuePrincipaleFragment.addView(vueInfo);

        LinearLayout vueBoutons = new LinearLayout(activity);
        vueBoutons.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        vueBoutons.setOrientation(LinearLayout.VERTICAL);
        vueInfo.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        );
        vuePrincipaleFragment.addView(vueBoutons);

        // NB: Si le point d'acces n'a pas ete assigne avant cette methode
        // alors il faut que ca crash
        if (pointAcces == null)
            throw new NullPointerException("Point d'acces n'est pas initialise");

        // Ajouter la vue du ssid
        vueSSID = new TextView(activity);
        vueInfo.addView(vueSSID);

        // Ajouter la vue du bssid
        vueBSSID = new TextView(activity);
        vueInfo.addView(vueBSSID);

        // Ajouter la vue du rssi
        vueRSSI = new TextView(activity);
        vueInfo.addView(vueRSSI);

        // Ajouter la vue pour afficher si c'est avec ou sans mot de passe
        vueAcces = new TextView(activity);
        vueInfo.addView(vueAcces);

        // Assigner les infos du point d'acces
        mettreVuesAJour();

        // Initialiser la vue pour ajouter/enlever ce point d'acces des favoris
        vueAjouterAuxFavoris = new Button(activity);
        vueAjouterAuxFavoris.setText("Ajouter aux favoris");
        vueAjouterAuxFavoris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.ajouterAuxFavoris(1); // essai
            }
        });
        vueBoutons.addView(vueAjouterAuxFavoris);

        // Initialiser la vue pour partager
        vuePartager = new Button(activity);
        vuePartager.setText("Partager");
        vuePartager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.partager(1); // essai
            }
        });
        vueBoutons.addView(vuePartager);

        // Initialiser la vue pour obtenir un chemin parmi ceux les plus courts
        // entre la position actuelle et ce point d'acces
        vueObtenirDirection = new Button(activity);
        vueObtenirDirection.setText("Obtenir direction");
        vueObtenirDirection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.obtenirDirection(1); // essai
            }
        });
        vueBoutons.addView(vueObtenirDirection);

        // TODO : Ajouter les autres vues
        // ...

        return vuePrincipaleFragment;
    }

    // Mettre les vues a jour en fonctions des infos du point d'acces.
    // Doit etre appele a chaque fois qu'un nouveau point d'acces est assigne.
    public void mettreVuesAJour() {
        vueSSID.setText("SSID : " + pointAcces.obtenirSSID());
        vueBSSID.setText("BSSID : " + pointAcces.obtenirBSSID());
        vueRSSI.setText("RSSI : " + pointAcces.obtenirRSSI());
        String messageAcces = pointAcces.estProtegeParMotDePasse() ? "Protege par mot de passe" : "Sans mot de passe";
        vueAcces.setText(messageAcces);
    }

    // Called when the fragment's activity has been created and this fragment's view hierarchy
    // instantiated. It can be used to do final initialization once these pieces are in place,
    // such as retrieving views or restoring state.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnDetailsInteractionListener {

        // Finalement, la fonctionnalité partager sert à envoyer l' information
        // d'un hotspot (SSID,BSSID,etc...) à un de nos contacts téléphoniques ou
        // à un contact d'une autre app ( facebook, whatsapp, etc...).
        void partager(int idPointAcces);

        void ajouterAuxFavoris(int idPointAcces);

        void enleverDesFavoris(int idPointAcces);

        Path.Direction obtenirDirection(int idPointAcces);
    }
}
