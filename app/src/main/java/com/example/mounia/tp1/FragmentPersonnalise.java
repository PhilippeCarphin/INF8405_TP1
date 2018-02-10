package com.example.mounia.tp1;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FragmentPersonnalise extends ListFragment implements AdapterView.OnItemClickListener{

    //private ListView viewGroup;
    private ArrayList<String> textesALister = new ArrayList(){
        {add("Tim Horton"); add("Mc Donald"); add("Farhat") ;}};
    private Activity activity;

    public FragmentPersonnalise() {
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
    public void onActivityCreated(Bundle saveInstanceState)
    {
        super.onActivityCreated(saveInstanceState);
        ArrayAdapter adapter= new ArrayAdapter(activity,R.layout.item_view,textesALister);
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }


 /*   @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        // Juste pour tester
        Toast.makeText(getActivity(), "Position : " + position, Toast.LENGTH_SHORT).show();
    }*/

 @Override
 public void onItemClick(AdapterView <?> parent, View view, int position, long id)
 {
     Toast.makeText(activity, "Clicked"+ position, Toast.LENGTH_LONG).show();

     try{
         ((OnNewItemSelectedListener)activity).onNewItemPicked(position);
     }catch(ClassCastException cce){}

 }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =  inflater.inflate(R.layout.fragment_personnalise, container, false);

        return view;

    }


    public interface OnNewItemSelectedListener{

     public void onNewItemPicked(int position);
    }
}
