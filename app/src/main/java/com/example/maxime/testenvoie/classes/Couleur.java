package com.example.maxime.testenvoie.classes;

import android.media.Image;
import android.widget.ImageView;

public class Couleur {
    private String nom;
    private ImageView image;

    public Couleur(String nom, ImageView image) {
        this.nom = nom;
        this.image = image;
    }

    public ImageView getImageView() {
        return this.image;
    }

    public String getNom() {
        return this.nom;
    }
}
