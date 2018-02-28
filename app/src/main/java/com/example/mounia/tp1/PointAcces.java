package com.example.mounia.tp1;

import android.graphics.Path;
import android.net.wifi.WifiInfo;

/**
 * Created by passenger on 2/15/2018.
 */

public class PointAcces
{
    // Un identificateur pour chaque point d'acces
    private int id;
    private static int compteur = 0;

    // Permet de savoir si ce point d'acces est un favori directement
    private boolean estFavori;

    // Le nom du réseau	écrit dans un format simple.
    private String ssid;

    // L’adresse MAC du	point d'accès.
    private String bssid;

    // L’intensité du signal reçu
    private int rssi;

    // TODO : Une description claire du mécanisme d’authentification, de la	gestion	des	clés et	du
    // schéma de chiffrement pris en charge	par	le point d’accès (WEP, WPA etc…).
    // ...

    // Est-ce la même chose que le ssid?
    private String nomEmplacement;

    // Catégorie d'hotspot. Cette information vous l'obtiendrez à l'aide
    // d'une méthode de la classe Wifi manager
    private boolean avecMotDePasse; // couleur rouge, sans mot de passe -> couleur verte

    // Constructeur
    public PointAcces(WifiInfo wifiInfo)
    {
        // TODO : Inititialiser le id avec un outil tel que UUID
        // ...
        this.id = compteur++;

        // Intercepter un wifi info null et retourner.  Possiblement on devrait changer le constructeur
        // Selon ce que j'ai lu, c'est wifiManager.getScanResults() qu'il faudrait utiliser.
        // ref : https://developer.android.com/reference/android/net/wifi/WifiManager.html#getScanResults()
        if(wifiInfo == null){
            this.ssid = "ssid";
            this.bssid = "bssid";
            this.avecMotDePasse = true;
            return;
        }

        // Initialiser le nom du reseau, l'adresse MAC et l'intensite du signal recu
        this.ssid  = wifiInfo.getSSID();
        this.bssid = wifiInfo.getBSSID();
        this.rssi  = wifiInfo.getRssi();

        // Initialement, ce n'est pas un favori
        estFavori = false;

        // TODO : Initialiser la variable pour le mecanisme d'authentification
        // ...

        // TODO : Initialiser les autres attributs
        // ...
    }

    public int obtenirID() { return this.id; }

    public boolean estFavori() { return estFavori; }

    public void ajouterAuxFavoris() { estFavori = true; }

    // Juste pour permettre d'enlever des favoris, même si ce n'est pas dans l'énoncé
    public void enleverDesFavoris() { estFavori = false; }

    public Path.Direction obtenirDirection()
    {
        // TODO ...
        return null;
    }

    public void assignerNomEmplacement(String nomEmplacement) {
        this.nomEmplacement = nomEmplacement;
    }

    public String obtenirNomEmplacement() { return this.nomEmplacement; }

    public void assignerAcces(boolean avecMotDePasse) { this.avecMotDePasse = avecMotDePasse; }

    public boolean estProtegeParMotDePasse() { return this.avecMotDePasse; }

    public void assignerSSID(String SSID) { this.ssid = SSID; }

    public String obtenirSSID() { return this.ssid; }

    public void assignerBSSID(String BSSID) { this.bssid = BSSID; }

    public String obtenirBSSID() { return bssid; }

    public void assignerRSSI(int rssi) { this.rssi = rssi; }

    public int obtenirRSSI() { return this.rssi; }

    public String toString() { return "SSID=" + this.ssid + ", BSSID=" + this.bssid ;}
}
