package com.example.maxime.testenvoie.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;

import com.example.maxime.testenvoie.JouerActivity;
import com.example.maxime.testenvoie.R;

import java.io.File;
import java.util.Iterator;

public class Joueur {

    private Case caseJ;
    Bitmap myBitmap;

    public Joueur(){
    }

    public void setCaseJ(Case pCase){
        caseJ = pCase;
    }

    public Case getCaseJ(){
        return caseJ;
    }

    public void deplacementJoueur(){
        for (int i = 0; i < JouerActivity.plateau.length; i++) {
            for (int j = 0; j < JouerActivity.plateau.length; j++) {
                if (JouerActivity.plateau[i][j] != this.getCaseJ()){
                    JouerActivity.plateau[i][j].getImage().setBackgroundResource(R.drawable.fondcase);
                    JouerActivity.plateau[i][j].caseNonClickable();
                }
            }
        }

        int posIJoueur = 0;
        int posJJoueur = 0;
        int posIMaxJoueur = 0;
        int posJMaxJoueur = 0;
        int posIMinJoueur = 0;
        int posJMinJoueur = 0;
        int totalDepla = 0;

        for (int i = 0; i < JouerActivity.plateau.length; i++){
            for (int j = 0; j < JouerActivity.plateau.length; j++){
                if (JouerActivity.plateau[i][j] == this.getCaseJ()){
                    posIJoueur = i;
                    posJJoueur = j;
                }
            }
        }

        if (posIJoueur + JouerActivity.deplacement >= JouerActivity.plateau.length){
            posIMaxJoueur = JouerActivity.plateau.length - 1;
        }else {
            posIMaxJoueur = posIJoueur + JouerActivity.deplacement;
        }

        if (posJJoueur + JouerActivity.deplacement >= JouerActivity.plateau.length){
            posJMaxJoueur = JouerActivity.plateau.length - 1;
        }else {
            posJMaxJoueur = posJJoueur + JouerActivity.deplacement;
        }

        if (posIJoueur - JouerActivity.deplacement <= 0){
            posIMinJoueur = 0;
        }else {
            posIMinJoueur = posIJoueur - JouerActivity.deplacement;
        }

        if (posJJoueur - JouerActivity.deplacement <= 0){
            posJMinJoueur = 0;
        }else {
            posJMinJoueur = posJJoueur - JouerActivity.deplacement;
        }

        for (int i = posIMinJoueur; i <= posIMaxJoueur; i++){
            for (int j = posJMinJoueur; j <= posJMaxJoueur; j++){
                if (JouerActivity.plateau[i][j] != this.getCaseJ()){
                    totalDepla = Math.abs(i - posIJoueur) + Math.abs(j - posJJoueur);
                    if (totalDepla <= JouerActivity.deplacement){
                        afficherCaseDeplacement(JouerActivity.plateau[i][j], JouerActivity.plateau[i][j].getImage());
                    }
                }
            }
        }
    }

    public void afficherCaseDeplacement(Case pCase, ImageButton pImage){
        pImage.setBackgroundResource(R.drawable.deplacement);
        pCase.caseClickable();
    }

    public void changerCaseJoueur(Case pCase, int i, int j){
        Iterator<Salle> iter = JouerActivity.listSalles.iterator();
        while (iter.hasNext()) {
            Salle element = iter.next();
            if (element.getCase() == pCase){
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.fondcase);
                this.getCaseJ().caseClickable();
                this.setCaseJ(JouerActivity.plateau[i][j]);
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawn);
                this.getCaseJ().caseNonClickable();
                JouerActivity.deplacement = 0;
                this.deplacementJoueur();
            } else {
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.fondcase);
                this.getCaseJ().caseClickable();
                this.setCaseJ(pCase);
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawn);
                this.getCaseJ().caseNonClickable();
                JouerActivity.deplacement = 0;
                this.deplacementJoueur();
            }
        }
    }
}
