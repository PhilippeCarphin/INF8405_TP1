package com.example.mounia.tp1;

import android.graphics.Path;

/**
 * Created by passenger on 2/15/2018.
 */

public class PointAcces
{
    // Est-ce la même chose que le SSID?
    private String nomEmplacement;

    // Les 7 types d'emplacement. Est-ce encore évalué?
    private TypeEmplacement typeEmplacement;

    // Les 3 catégories de points d'accès spécifiés dans l'énoncé
    private CategoriePointAcces categoriePointAcces;

    // Le nom du réseau	écrit dans un format simple.
    private String SSID;

    // L’adresse MAC du	point d'accès.
    private String BSSID;

    // Constructeur
    public PointAcces()
    {
        // TODO : Initialiser les attributs
        // ...
    }

    // Partager la direction de l'endroit où on se situe à ce point d'accès
    // avec la liste de contacts.
    public void partagerDirection()
    {
        // TODO ...
    }

    public void ajouterAuxFavoris()
    {
        // TODO ...
    }

    // Juste pour permettre d'enlever des favoris, meme ce n'est pas dans l<enonce
    public void enleverDesFavoris()
    {
        // TODO ...
    }

    public Path.Direction obtenirDirection()
    {
        // TODO ...
        return null;
    }

    public void assignerNomEmplacement(String nomEmplacement) {
        this.nomEmplacement = nomEmplacement;
    }

    public String obtenirNomEmplacement() {
        return this.nomEmplacement;
    }

    public void assignerTypeEmplacement(TypeEmplacement typeEmplacement) {
        this.typeEmplacement = typeEmplacement;
    }

    public TypeEmplacement obtenirTypeEmplacement() {
        return this.typeEmplacement;
    }

    public void assignerCategorieHotspot(CategoriePointAcces categoriePointAcces) {
        this.categoriePointAcces = categoriePointAcces;
    }

    public CategoriePointAcces obtenirCategoryHotspot() {
        return this.categoriePointAcces;
    }

    public void assignerSSID(String SSID) {
        this.SSID = SSID;
    }

    public String obtenirSSID() {
        return this.SSID;
    }

    public void assignerBSSID(String BSSID) {
        this.BSSID = BSSID;
    }

    public String obtenirBSSID() {
        return BSSID;
    }
}
