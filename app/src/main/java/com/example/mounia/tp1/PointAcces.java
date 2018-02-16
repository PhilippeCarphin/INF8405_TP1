package com.example.mounia.tp1;

/**
 * Created by passenger on 2/15/2018.
 */

public class Hotspot {

    private String nomEmplacement;

    public void assignerNomEmplacement(String nomEmplacement) {
        this.nomEmplacement = nomEmplacement;
    }

    public String obtenirNomEmplacement() {
        return this.nomEmplacement;
    }

    private TypeEmplacement typeEmplacement;

    public void assignerTypeEmplacement(TypeEmplacement typeEmplacement) {
        this.typeEmplacement = typeEmplacement;
    }

    public TypeEmplacement obtenirTypeEmplacement() {
        return this.typeEmplacement;
    }

    private CategorieHotspot categorieHotspot;

    public void assignerCategorieHotspot(CategorieHotspot categorieHotspot) {
        this.categorieHotspot = categorieHotspot;
    }

    public CategorieHotspot obtenirCategoryHotspot() {
        return this.categorieHotspot;
    }

    // Le nom du réseau	écrit dans un format simple.
    private String SSID;

    public void assignerSSID(String SSID) {
        this.SSID = SSID;
    }

    public String obtenirSSID() {
        return this.SSID;
    }

    // L’adresse MAC du	point d'accès.
    private String BSSID;

    public void assignerBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String obtenirBSSID() {
        return BSSID;
    }
}
