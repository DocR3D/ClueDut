package com.example.maxime.testenvoie;

import android.widget.ImageButton;

public class CaseJoueur extends Case {

    private Joueur joueur;

    public CaseJoueur(ImageButton pImage, Joueur pJoueur){
        super(pImage);
        joueur = pJoueur;
    }
}
