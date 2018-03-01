package com.example.mounia.tp1;

import android.graphics.Path;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
//import  com.android.settings.wifi.AccessPointState;

/**
 * Created by passenger on 2/15/2018.
 */

public class PointAcces
{
    // Un identificateur pour chaque point d'acces
    private int id;
    private static int compteur = 0;

    // Le nom du réseau	écrit dans un format simple.
    private String ssid;

    // L’adresse MAC du	point d'accès.
    private String bssid;

    // L’intensité du signal reçu
    private int rssi;

    // Permet de savoir si ce point d'acces est un favori directement
    private boolean estFavori;

    // Une description claire du mécanisme d’authentification, de la	gestion	des	clés et	du
    // schéma de chiffrement pris en charge	par	le point d’accès (WEP, WPA etc…).
    private String capabilities;

    // Est-ce la même chose que le ssid?
    //private String nomEmplacement;

    // Catégorie d'hotspot. Cette information vous l'obtiendrez à l'aide
    // d'une méthode de la classe Wifi manager
    private boolean avecMotDePasse; // couleur rouge, sans mot de passe -> couleur verte

    // Constructeur qui prend un ScanResult
    // ref : https://developer.android.com/reference/android/net/wifi/WifiManager.html#getScanResults()

    /**
     * Constructeur de base pour créer un PointAcces avec SSID, BSSID et RSSID et valeurs par défaut
     * pour les autres attributs
     * @param SSID
     * @param BSSID
     * @param RSSI
     */
    public PointAcces(String SSID, String BSSID, int RSSI)
    {
        // TODO : Inititialiser le id avec un outil tel que UUID...
        this.id = compteur;
        compteur++;

        this.ssid = SSID;
        this.bssid = BSSID;
        this.rssi = -1;
        this.estFavori = false;
        this.capabilities = "";
        this.avecMotDePasse = false;
    }

    /**
     * Factory de PointAcces pour créer un PointAcces à partir d'un ScanResult
     * @param sr
     * @return
     */
    static public PointAcces PointAccesFromScanResult(ScanResult sr){
        // Philippe : Intercepter un ScanResult null et retourner.
        // Reph : C'est possible d'intercepter en effet. Toutefois, ici, il semble qu'il nous
        // faut les infos et donc scanResult ne doit pas etre null. Donc, ca devrait crash
        // pour qu'on decouvre ce bug avant la sortie du programme en production.
        // => Utilisation d'un assert serait bien.
        if (sr == null)
            throw new NullPointerException("ScanResult is null");

        PointAcces pa = new PointAcces(sr.SSID, sr.BSSID, sr.level);

        pa.estFavori = false;
        pa.capabilities = sr.capabilities;

        return pa;
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

//    public void assignerNomEmplacement(String nomEmplacement) {
//        this.nomEmplacement = nomEmplacement;
//    }
//
//    public String obtenirNomEmplacement() { return this.nomEmplacement; }

    public void assignerAcces(boolean avecMotDePasse) { this.avecMotDePasse = avecMotDePasse; }

    public boolean estProtegeParMotDePasse() { return this.avecMotDePasse; }

    public void assignerSSID(String SSID) { this.ssid = SSID; }

    public String obtenirSSID() { return this.ssid; }

    public void assignerBSSID(String BSSID) { this.bssid = BSSID; }

    public String obtenirBSSID() { return bssid; }

    public void assignerRSSI(int rssi) { this.rssi = rssi; }

    public int obtenirRSSI() { return this.rssi; }

    public String obtenirCapabilities() { return capabilities; }

    public void assignerCapabilities(String capabilities) { this.capabilities = capabilities; }

    public String toString() { return "SSID=" + this.ssid + ", BSSID=" + this.bssid ;}
}
