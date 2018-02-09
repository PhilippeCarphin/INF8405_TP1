package com.example.mounia.tp1;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentPersonnalise extends Fragment {

    private ViewGroup viewGroup;

    public FragmentPersonnalise() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }

        // Initialiser la liste de textes
        /*textesALister = new ArrayList<>();

        // Juste pour tester, mettre des valeurs hardcodées
        textesALister.add("Tim Horton");
        textesALister.add("Mc Donald");
        textesALister.add("Farhat");
*/
        // Specificier l'adapteur de type liste du fragment
       // setListAdapter(new AdaptateurListePersonnalisee(getActivity(), textesALister));
    }


   /* @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        // Juste pour tester
        Toast.makeText(getActivity(), "Position : " + position, Toast.LENGTH_SHORT).show();
    }
*/
    // Les textes a lister
    //private ArrayList<String> textesALister;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_personnalise, container, false);


        viewGroup = (ViewGroup) view.findViewById(R.id.myListView);

        TextView aux = new TextView(getActivity());

       aux.setText("Hello World!");

        viewGroup.addView(aux);
//

        return view;

    }


}
