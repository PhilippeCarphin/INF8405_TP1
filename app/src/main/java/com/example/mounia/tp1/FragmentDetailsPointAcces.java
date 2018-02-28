package com.example.mounia.tp1;

import android.content.Context;
import android.graphics.Path;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnDetailsInteractionListener} interface
 * to handle interaction events.
 */
public class FragmentDetailsPointAcces extends Fragment {

    private OnDetailsInteractionListener mListener;

    public FragmentDetailsPointAcces() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsInteractionListener) {
            mListener = (OnDetailsInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnDetailsInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private Button boutonAjouterAuxFavoris;
    // ajouter autres boutons

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
