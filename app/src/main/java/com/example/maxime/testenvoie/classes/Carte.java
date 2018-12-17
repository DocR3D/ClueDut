package com.example.maxime.testenvoie.classes;
// c'est moi qui l'ai fait
class Carte {



    private String name;
    private Type type;
    public enum Type {SALLE,ARME, PERSONNAGE}


    public Carte(String name, Type type){
        this.name = name;
        this.type = type;
    }

    public String getNom(){
        return this.name;
    }


    public Type getType() {
        return type;
    }
}
