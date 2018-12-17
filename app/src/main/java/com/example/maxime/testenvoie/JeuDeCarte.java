package com.example.maxime.testenvoie;

import java.util.ArrayList;
import java.util.Collections;

public class JeuDeCarte {
    ArrayList jeuDeCartes = new ArrayList<Carte>();
    String[] nomSalles = {"Cuisine", "Salle de bal", "Veranda", "Salle de billard", "Bibiliothèque", "Bureau", "Hall", "Salon ", "Salle à manger"};
    String[] nomArmes = {"Corde", "Poignard", "Barre de fer", "Revolver","Chandelier", "Clé anglaise"};
    String[] nomPersonnage = {"Mr Moutard","Mlle Rose", "Mr Olive","Mme LeBlanc","Mme Pervenche", "Professeur Violet"};
    ArrayList<Carte> lesCartesImportantes = new ArrayList<Carte>();

    public JeuDeCarte(){
        for(int i=0; i<9;i++) jeuDeCartes.add(new Carte(nomSalles[i], Carte.Type.SALLE));
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomArmes[i], Carte.Type.ARME));
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomPersonnage[i], Carte.Type.PERSONNAGE));
    }

    public void melanger(){
        Collections.shuffle(jeuDeCartes);
    }

    public Carte takeCard(){
        Carte uneCarte= (Carte) jeuDeCartes.get(jeuDeCartes.size());
        jeuDeCartes.remove(uneCarte);
        return uneCarte;
    }

    public ArrayList takeAllTypeCard() {
        ArrayList<Carte> lesCartes = new ArrayList<Carte>();
        Carte uneCarte = new Carte("Qqchose", Carte.Type.ARME);
        while (uneCarte.getType() != Carte.Type.SALLE) {
            int i = 0;
            if (((Carte) jeuDeCartes.get(i)).getType() == Carte.Type.SALLE) {
                uneCarte = (Carte) jeuDeCartes.get(i);
                lesCartes.add(uneCarte);
            }
            i++;
        }

        while (uneCarte.getType() != Carte.Type.ARME) {
            int i = 0;
            if (((Carte) jeuDeCartes.get(i)).getType() == Carte.Type.ARME) {
                uneCarte = (Carte) jeuDeCartes.get(i);
                lesCartes.add(uneCarte);
            }
            i++;
        }

        while (uneCarte.getType() != Carte.Type.PERSONNAGE) {
            int i = 0;
            if (((Carte) jeuDeCartes.get(i)).getType() == Carte.Type.PERSONNAGE) {
                uneCarte = (Carte) jeuDeCartes.get(i);
                lesCartes.add(uneCarte);
            }
            i++;
        }
        lesCartesImportantes.add(lesCartes);
        return lesCartes;
    }

    public boolean compareCard(Carte uneCarte, Carte uneDeuxieme, Carte uneTroisieme){
        if(uneCarte.getNom() == lesCartesImportantes.get(1).getNom()){

        }
        return true;
    }


}
