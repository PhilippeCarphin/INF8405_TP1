package com.example.mounia.tp1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mounianordine on 18-02-07.
 */

public class AdaptateurListePersonnalisee extends ArrayAdapter<String> {

    // Un liste de toutes les images possibles
    private Integer[] imagesALister;

    // Le constructeur
    public AdaptateurListePersonnalisee(Context context, ArrayList<String> textesALister) {
        super(context, R.layout.fragment_personnalise, textesALister);

        // Initialiser la liste d'images
        imagesALister = new Integer[] {
                R.mipmap.coffe_symbol,
                R.mipmap.hotel_symbol,
                R.mipmap.wifi_symbol
        };
    }

    // Surcharger la méthode getView
    @Override
    public View getView(int position, View vueDeConversion, ViewGroup parent) {

        // Accéder au gonfleur de layout
        LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // L'utiliser pour gonfler une vue d'une ligne
        View vueDuneLigne = inflater.inflate(R.layout.fragment_personnalise, parent, false);

        // Récupérer les vues image et texte depuis leur xml
       /* ImageView imageView = vueDuneLigne.findViewById(R.id.symbol);
        TextView textView = vueDuneLigne.findViewById(R.id.info);

        // Affecter le texte approprié au textview
        textView.setText(getItem(position));

        if (vueDeConversion == null)
            imageView.setImageResource(imagesALister[position]);
        else
            vueDuneLigne = vueDeConversion;
*/
        return vueDuneLigne;
    }

}
