package com.example.mounia.navigationdrawer;
import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FragmentItems extends ListFragment {

    String[] text = new String[] {
            "Poly",
            "Café",
            "Hotel",

    };
    int[] image = new int[] {
            R.mipmap.coffe_symbol,
            R.mipmap.hotel_symbol,
            R.mipmap.wifi_symbol
    };
    private Activity activity;

    public FragmentItems() {
        // Required empty public constructor
    }
    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        if(context instanceof Activity)
        {
            activity= (Activity) context;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

        for(int i=0;i<3;i++){
            HashMap<String, String> hm = new HashMap<String,String>();
            hm.put("image", Integer.toString(image[i]));
            hm.put("text" , text[i]);
            aList.add(hm);
        }

        // Keys used in Hashmap
        String[] from = { "text","image"};

        // Ids of views in listview_layout
        int[] to = { R.id.texte,R.id.image};

        SimpleAdapter adapter = new SimpleAdapter(getActivity().getBaseContext(), aList, R.layout.item_view, from, to);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
