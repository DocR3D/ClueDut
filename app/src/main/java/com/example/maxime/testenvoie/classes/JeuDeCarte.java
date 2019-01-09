package com.example.maxime.testenvoie.classes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JeuDeCarte {
    ArrayList<Carte> jeuDeCartes = new ArrayList();
    String[] nomSalles = {"Cuisine", "Salle de bal", "Veranda", "Salle de billard", "Bibiliothèque", "Bureau", "Hall", "Salon ", "Salle à manger"};
    String[] nomArmes = {"Corde", "Poignard", "Barre de fer", "Revolver","Chandelier", "Clé anglaise"};
    String[] nomPersonnage = {"Mr Moutard","Mlle Rose", "Mr Olive","Mme LeBlanc","Mme Pervenche", "Professeur Violet"};

    public JeuDeCarte(){
        for(int i=0; i<9;i++) jeuDeCartes.add(new Carte(nomSalles[i], Carte.Type.SALLE));
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomArmes[i], Carte.Type.ARME));
        for(int i=0; i<6;i++) jeuDeCartes.add(new Carte(nomPersonnage[i], Carte.Type.PERSONNAGE));
    }

    public void melanger(){
        System.out.println("C'est la !");
        Collections.shuffle(jeuDeCartes);
    }

    public Carte takeCard(){
        System.out.println("jdsfbujsdbfisdbfisdfgi");
        int index = jeuDeCartes.size() -1;
        Carte uneCarte= jeuDeCartes.get(index);
        jeuDeCartes.remove(uneCarte);
        return uneCarte;
    }

    public ArrayList takeAllTypeCard() {
        ArrayList<Carte> lesCartesImportantes = new ArrayList<Carte>();
        List<Carte> carteAEnlever = new ArrayList<>();
        //Carte uneCarte = new Carte(nomPersonnage[(int)(Math.random()* nomPersonnage.length)],Carte.Type.PERSONNAGE);
        int index = (int) Math.random() * 8;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        //uneCarte = new Carte(nomArmes[(int)(Math.random()* nomArmes.length)],Carte.Type.ARME);
        index = (int) (Math.random() * 6) + 9;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        //uneCarte = new Carte(nomSalles[(int)(Math.random()* nomSalles.length)],Carte.Type.SALLE);
        index = (int) (Math.random() * 6) + 15;
        lesCartesImportantes.add(jeuDeCartes.get(index));
        carteAEnlever.add(jeuDeCartes.get(index));
        jeuDeCartes.removeAll(carteAEnlever);
        return lesCartesImportantes;
    }

    public int getSizeJeuDeCarte() {
        return jeuDeCartes.size();
    }


}
