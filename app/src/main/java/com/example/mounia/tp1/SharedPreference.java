package com.example.mounia.tp1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.google.gson.Gson;

/**
 * Created by mounianordine on 18-02-26.
 */


public class SharedPreference {

    public static final String PREFS_NAME = "POINTACCES_APP";
    public static final String FAVORITES = "PointAcces_Favorite";

    public SharedPreference() {
        super();
    }

    // This four methods are used for maintaining favorites.
    public void saveFavorites(Context context, List<PointAcces> favorites) {
        SharedPreferences settings;
        Editor editor;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(favorites);

        editor.putString(FAVORITES, jsonFavorites);

        editor.commit();
    }

    public void addFavorite(Context context, PointAcces pointAcces) {
        List<PointAcces> favorites = getFavorites(context);
        if (favorites == null)
            favorites = new ArrayList<PointAcces>();
        favorites.add(pointAcces);
        saveFavorites(context, favorites);
    }

    public void removeFavorite(Context context, PointAcces pointAcces) {
        ArrayList<PointAcces> favorites = getFavorites(context);
        if (favorites != null) {
            favorites.remove(pointAcces);
            saveFavorites(context, favorites);
        }
    }

    public ArrayList<PointAcces> getFavorites(Context context) {
        SharedPreferences settings;
        List<PointAcces> favorites;

        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        if (settings.contains(FAVORITES)) {
            String jsonFavorites = settings.getString(FAVORITES, null);
            Gson gson = new Gson();
            PointAcces[] favoriteItems = gson.fromJson(jsonFavorites,
                    PointAcces[].class);

            favorites = Arrays.asList(favoriteItems);
            favorites = new ArrayList<PointAcces>(favorites);
        } else
            return null;

        return (ArrayList<PointAcces>) favorites;
    }
}
