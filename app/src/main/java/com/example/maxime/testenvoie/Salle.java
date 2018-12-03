package com.example.maxime.testenvoie;

public class Salle {

    private Joueur joueur;
    private String nom;
    private Case caseC;

    public Salle(String pNom, Case pCase){
        caseC = pCase;
        nom = pNom;
    }

    public String getNom(){
        return nom;
    }

    public Case getCase(){
        return caseC;
    }
}
