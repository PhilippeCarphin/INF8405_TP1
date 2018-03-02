package com.example.mounia.tp1;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FragmentListePointsAcces extends ListFragment
{
    // Toujours utiliser la meme image, vue qu'il n'y a plus les 7 types d'emplacement
    private int[] image = new int[] { R.mipmap.wifi_symbol };

    // Pour contenir les SSID
    private String[] text = null;

    // Pour contenir les points d'acces detectes au dernier scan
    private List<PointAcces> pointsAccesDetectes = null;

    // Permet de correspondre les indices de la liste vue aux ids des points d'acces
    private Map<Integer, Integer> indicesEtIdPointsAcces = null;

    // Pour pointer vers l'activite parent de ce fragment
    private Activity activity = null;

    // La vue principale de ce fragment
    private ListView listFragmentListView = null;

    // Permet de passer des messages à l'activité parent de ce fragment
    private OnPointAccesSelectedListener mCallback = null;

    public FragmentListePointsAcces() {
        // Required empty public constructor
    }


    // Container Activity must implement this interface
    public interface OnPointAccesSelectedListener {
        void onPointAccesSelected(int idPointAcces);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof Activity)
            activity = (Activity) context;

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            mCallback = (OnPointAccesSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFavorisSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Note: If your fragment is a subclass of ListFragment, the default
        // implementation returns a ListView from onCreateView()

        // Arrivé ici, la méthode assignerPointsAcces de ce fragment devrait déjà être
        // appelée. Si elle est null, on devrait découvrir le bug.
//        if (this.pointsAccesDetectes == null)
//            throw new NullPointerException("pointsAccesDetectes is null");

        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < this.pointsAccesDetectes.size(); i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("image", Integer.toString(image[0]));
            hm.put("text", text[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"text", "image"};

        // Ids of views in listview_layout
        int[] to = {R.id.texte, R.id.image};

        SimpleAdapter adapter = new SimpleAdapter(activity.getBaseContext(), aList, R.layout.item_view, from, to);
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void assignerPointsAccesDetectes(List<PointAcces> pointsAcces) {
        if (pointsAcces != null) {
            // Se rappeler des derniers points d'acces detectes
            this.pointsAccesDetectes = pointsAcces;

            // Initialiser text et la table de correspondance d'indices de la liste et des ids de points d'acces
            text = new String[pointsAcces.size()];
            indicesEtIdPointsAcces = new HashMap<>();

            // Remplir le tableau text
            for (int i = 0; i < pointsAcces.size(); i++) {
                text[i] = pointsAcces.get(i).obtenirSSID();

                // et profiter de ce parcours de boucle pour matcher les indices
                // avec les ids des points d'acces
                indicesEtIdPointsAcces.put(i, pointsAcces.get(i).obtenirID());
            }
        }
    }

    // Called when the fragment's activity has been created and this fragment's view hierarchy
    // instantiated. It can be used to do final initialization once these pieces are in place,
    // such as retrieving views or restoring state.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Recuperer la ListView associee a ce fragment
        listFragmentListView = getListView();

        listFragmentListView.setBackgroundColor(Color.rgb(255, 255, 255));

        // Associer un observateur aux selections d'items de la liste
        listFragmentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Passer le message a l'activite parent
                mCallback.onPointAccesSelected(indicesEtIdPointsAcces.get(position));
            }
        });
    }

    @Override
    public void onPause() { super.onPause(); }
}
