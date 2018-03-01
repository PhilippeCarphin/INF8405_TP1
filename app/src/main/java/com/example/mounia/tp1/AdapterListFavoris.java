package com.example.mounia.tp1;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by mounianordine on 18-02-28.
 */

public class AdapterListFavoris extends ArrayAdapter<PointAcces> {

        private Activity activity;

        public AdapterListFavoris(Activity activity, int resource, List<PointAcces> pointsAcces) {
            super(activity, resource, pointsAcces);
            this.activity = activity;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            LayoutInflater inflater = (LayoutInflater) activity
                    .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            // If holder not exist then locate all view from UI file.
            if (convertView == null) {
                // inflate UI from XML file
                convertView = inflater.inflate(R.layout.list_favoris, parent, false);
                // get all UI view
                holder = new ViewHolder(convertView);
                // set tag for holder
                convertView.setTag(holder);
            } else {
                // if holder created, get tag from view
                holder = (ViewHolder) convertView.getTag();
            }

            PointAcces pointAcces = getItem(position);

            holder.name.setText(pointAcces.obtenirSSID());


            return convertView;
        }

        private static class ViewHolder {
            private TextView name;
            public ViewHolder(View v) {
                name = (TextView) v.findViewById(R.id.textFavoris);

            }
        }
}
