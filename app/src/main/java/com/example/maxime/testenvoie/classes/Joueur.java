package com.example.maxime.testenvoie.classes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageButton;

import com.example.maxime.testenvoie.JouerActivity;
import com.example.maxime.testenvoie.Menu;
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

    public void deplacementJoueur(int deplacement){
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

        if (posIJoueur + deplacement >= JouerActivity.plateau.length){
            posIMaxJoueur = JouerActivity.plateau.length - 1;
        }else {
            posIMaxJoueur = posIJoueur + deplacement;
        }

        if (posJJoueur + deplacement >= JouerActivity.plateau.length){
            posJMaxJoueur = JouerActivity.plateau.length - 1;
        }else {
            posJMaxJoueur = posJJoueur + deplacement;
        }

        if (posIJoueur - deplacement <= 0){
            posIMinJoueur = 0;
        }else {
            posIMinJoueur = posIJoueur - deplacement;
        }

        if (posJJoueur - deplacement <= 0){
            posJMinJoueur = 0;
        }else {
            posJMinJoueur = posJJoueur - deplacement;
        }

        for (int i = posIMinJoueur; i <= posIMaxJoueur; i++){
            for (int j = posJMinJoueur; j <= posJMaxJoueur; j++){
                if (JouerActivity.plateau[i][j] != this.getCaseJ()){
                    totalDepla = Math.abs(i - posIJoueur) + Math.abs(j - posJJoueur);
                    if (totalDepla <= deplacement){
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

    public void changerCaseJoueur(Case pCase, final int i, final int j, int numJoueur){
        Iterator<Salle> iter = JouerActivity.listSalles.iterator();
        while (iter.hasNext()) {
            Salle element = iter.next();
            if (element.getCase() == pCase){
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.fondcase);
                this.getCaseJ().caseClickable();
                this.setCaseJ(JouerActivity.plateau[i][j]);
                switch (Menu.client.couleurs[numJoueur]){
                    case 0:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                        break;
                    case 1:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                        break;
                    case 2:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                        break;
                    case 3:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                        break;
                }
                this.getCaseJ().caseNonClickable();
                JouerActivity.deplacement = 0;
                this.deplacementJoueur(0);
            } else {
                this.getCaseJ().getImage().setBackgroundResource(R.drawable.fondcase);
                this.getCaseJ().caseClickable();
                this.setCaseJ(pCase);
                switch (Menu.client.couleurs[numJoueur]){
                    case 0:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                        break;
                    case 1:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                        break;
                    case 2:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                        break;
                    case 3:
                        this.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                        break;
                }
                this.getCaseJ().caseNonClickable();
                JouerActivity.deplacement = 0;
                this.deplacementJoueur(0);
            }
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Menu.client.move(i,j);
            }
        });
        thread.start();
        thread.interrupt();

        afficherAutreJoueur(numJoueur);
    }

    public static void afficherAutreJoueur(int numJoueur) {
        for (int i = 0; i < 4; i++){
            if (i != numJoueur){
                if (i == 1){
                    switch (Menu.client.couleurs[i]){
                        case 0:
                            JouerActivity.j2.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                            break;
                        case 1:
                            JouerActivity.j2.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                            break;
                        case 2:
                            JouerActivity.j2.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                            break;
                        case 3:
                            JouerActivity.j2.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                            break;
                    }
                }

                if (i == 2){
                    switch (Menu.client.couleurs[i]){
                        case 0:
                            JouerActivity.j3.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                            break;
                        case 1:
                            JouerActivity.j3.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                            break;
                        case 2:
                            JouerActivity.j3.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                            break;
                        case 3:
                            JouerActivity.j3.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                            break;
                    }
                }

                if (i == 3){
                    switch (Menu.client.couleurs[i]){
                        case 0:
                            JouerActivity.j4.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                            break;
                        case 1:
                            JouerActivity.j4.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                            break;
                        case 2:
                            JouerActivity.j4.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                            break;
                        case 3:
                            JouerActivity.j4.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                            break;
                    }
                }

                if (i == 0){
                    switch (Menu.client.couleurs[i]){
                        case 0:
                            JouerActivity.j1.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnrouge);
                            break;
                        case 1:
                            JouerActivity.j1.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnvert);
                            break;
                        case 2:
                            JouerActivity.j1.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnbleu);
                            break;
                        case 3:
                            JouerActivity.j1.getCaseJ().getImage().setBackgroundResource(R.drawable.pawnviolet);
                            break;
                    }
                }
            }
        }
    }
}
