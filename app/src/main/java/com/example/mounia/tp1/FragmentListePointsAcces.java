package com.example.mounia.tp1;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentListePointsAcces extends ListFragment
{
    private int[] image = new int[] {
            R.mipmap.coffe_symbol,
            R.mipmap.hotel_symbol,
            R.mipmap.wifi_symbol
    };

    private String[] text = new String[] {
            "Café",
            "Hotel",
            "Poly",
    };

    private Activity activity;

    public FragmentListePointsAcces() {
        // Required empty public constructor
    }

    @Override
    public void onListItemClick(ListView listeCliquee, View itemClique, int positionItemClique, long idRangeeItemClique) {
        // Send the event to the host activity
        mCallback.onPointAccesSelected(positionItemClique);
    }

    private OnPointAccesSelectedListener mCallback;

    // Container Activity must implement this interface
    public interface OnPointAccesSelectedListener {
        public void onPointAccesSelected(int position);
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
                    + " must implement OnHeadlineSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        // Note: If your fragment is a subclass of ListFragment, the default
        // implementation returns a ListView from onCreateView()

        // La conversion en ListView fait crasher l'app
        //ListView listView = (ListView) super.onCreateView(inflater, container, savedInstanceState);

        /*
        List<HashMap<String, String>> aList = new ArrayList<HashMap<String, String>>();
        for (int i = 0; i < 3; i++) {
            HashMap<String, String> hm = new HashMap<String, String>();
            hm.put("image", Integer.toString(image[i]));
            hm.put("text", text[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = {"text", "image"};

        // Ids of views in listview_layout
        int[] to = {R.id.texte, R.id.image};

        SimpleAdapter adapter = new SimpleAdapter(activity.getBaseContext(), aList, R.layout.item_view, from, to);
        setListAdapter(adapter);
        */

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Called when the fragment's activity has been created and this fragment's view hierarchy
    // instantiated. It can be used to do final initialization once these pieces are in place,
    // such as retrieving views or restoring state.
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Populate list with our static array of titles.
        //HashMap<>
        //ListAdapter arrayAdapter = new ArrayAdapter<String>(getActivity(), liste, )
        //setListAdapter(arrayAdapter);
    }

    @Override
    public void onPause() { super.onPause(); }
}
