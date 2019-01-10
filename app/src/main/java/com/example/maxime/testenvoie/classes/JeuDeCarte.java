package com.example.maxime.testenvoie.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JeuDeCarte {
    ArrayList<Carte> jeuDeCartes = new ArrayList();
    String[] nomSalles = {"Cuisine", "Salle_de_Bal", "Vérenda", "Salle_de_Billard", "Bibliothèque", "Bureau", "Hall", "Salon", "Salle_à_Manger"};
    String[] nomArmes = {"Corde", "Poignard", "Matraque", "Revolver","Chandelier", "Clé_Anglaise"};
    String[] nomPersonnage = {"Colonel_Moutarde","Mademoiselle_Rose", "Reverand_Olive","Madame_Leblanc","Madame_Pervenche", "Professeur_Violet"};

    public JeuDeCarte(){
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomPersonnage[i], Carte.Type.PERSONNAGE));
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomArmes[i], Carte.Type.ARME));
        for(int i=0; i<9;i++) jeuDeCartes.add(new Carte(nomSalles[i], Carte.Type.SALLE));
    }

    public void melanger(){
        Collections.shuffle(jeuDeCartes);
    }

    public Carte takeCard(){
        int index = jeuDeCartes.size() -1;
        Carte uneCarte= jeuDeCartes.get(index);
        jeuDeCartes.remove(uneCarte);
        return uneCarte;
    }

    public ArrayList takeAllTypeCard() {
        ArrayList<Carte> lesCartesImportantes = new ArrayList<Carte>();
        List<Carte> carteAEnlever = new ArrayList<>();
        //Carte uneCarte = new Carte(nomPersonnage[(int)(Math.random()* nomPersonnage.length)],Carte.Type.PERSONNAGE);
        int index = (int) Math.random() * 5;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        //uneCarte = new Carte(nomArmes[(int)(Math.random()* nomArmes.length)],Carte.Type.ARME);
        index = (int) (Math.random() * 5) + 6;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        //uneCarte = new Carte(nomSalles[(int)(Math.random()* nomSalles.length)],Carte.Type.SALLE);
        index = (int) (Math.random() * 8) + 12;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        jeuDeCartes.removeAll(carteAEnlever);
        return lesCartesImportantes;
    }

    public int getSizeJeuDeCarte() {
        return jeuDeCartes.size();
    }


}
