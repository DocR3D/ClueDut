package com.example.maxime.testenvoie.classes;

import android.widget.ImageButton;

public class CaseJoueur extends Case {

    private Joueur joueur;

    public CaseJoueur(ImageButton pImage, Joueur pJoueur){
        super(pImage);
        joueur = pJoueur;
    }
}
