package com.example.maxime.testenvoie;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maxime.testenvoie.R;
import com.example.maxime.testenvoie.classes.Couleur;
import com.example.maxime.testenvoie.classes.Server;

import java.util.ArrayList;

public class SalonRejoindrePartie extends AppCompatActivity {

    //Pour ne pas que le onFocusChange se refasse plein de fois
    public boolean firstFocus = true;

    public static SalonRejoindrePartie context;

    //Interface Salon
    public LinearLayout cadreJoueurs;
    public ImageButton backToMenuSalonRejoindrePartie_btn;
    public ImageButton pret_btn;
    public static TextView nomJoueur1;
    public static TextView nomJoueur2;
    public static TextView nomJoueur3;
    public static TextView nomJoueur4;

    //Popup ChoisirCouleur
    public Dialog creationPartiePopup;
    public LinearLayout cadreCreationPartie;;
    public ImageView texteCouleurCreationPartie;
    public ImageButton backToPlayPopupCreationPartie_btn;
    public ImageButton goToSalonCreation_btn;
    public ImageButton redCreationPartie;
    public ImageButton greenCreationPartie;
    public ImageButton blueCreationPartie;
    public ImageButton purpleCreationPartie;
    public ImageButton selectedColorCreationPartie;
    public String couleurChoisieCreationPartie = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_rejoindre_partie);
        cadreJoueurs = findViewById(R.id.cadreJoueursRejoindrePartie);
        backToMenuSalonRejoindrePartie_btn = findViewById(R.id.backToMenuSalonRejoindrePartie);
        pret_btn = findViewById(R.id.pret);
        nomJoueur1 = findViewById(R.id.nomJoueur1);
        nomJoueur2 = findViewById(R.id.nomJoueur2);
        nomJoueur3 = findViewById(R.id.nomJoueur3);
        nomJoueur4 = findViewById(R.id.nomJoueur4);
        context = this;
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        if (firstFocus) {
            setComponentsSize();
            setComponentsListeners();
            firstFocus = false;
        }
    }

    private void setComponentsListeners() {

        creationPartiePopup = new Dialog(SalonRejoindrePartie.this);
        creationPartiePopup.setContentView(R.layout.popup_choisir_couleur);
        cadreCreationPartie = creationPartiePopup.findViewById(R.id.cadreCreationPartie);
        // cadreCouleursCreationPartie = choixAction.findViewById(R.id.cadreCouleursCreationPartie);
        backToPlayPopupCreationPartie_btn = creationPartiePopup.findViewById(R.id.backToPlayPopupCreationPartie);
        texteCouleurCreationPartie = creationPartiePopup.findViewById(R.id.textCouleurCreationpartie);
        goToSalonCreation_btn = creationPartiePopup.findViewById(R.id.goToSalonCreation);
        redCreationPartie = creationPartiePopup.findViewById(R.id.redCreationPartie);
        greenCreationPartie = creationPartiePopup.findViewById(R.id.greenCreationPartie);
        blueCreationPartie = creationPartiePopup.findViewById(R.id.blueCreationPartie);
        purpleCreationPartie = creationPartiePopup.findViewById(R.id.purpleCreationPartie);
        Couleur rouge = new Couleur( "rouge", redCreationPartie);
        Couleur vert = new Couleur( "vert", greenCreationPartie);
        Couleur bleu = new Couleur( "bleu", blueCreationPartie);
        Couleur violet = new Couleur( "violet", purpleCreationPartie);
        selectedColorCreationPartie = creationPartiePopup.findViewById(R.id.selectedColorCreationPartie);


        final ArrayList<Couleur> couleurs= new ArrayList<Couleur>();
            couleurs.add(rouge);
            couleurs.add(vert);
            couleurs.add(bleu);
            couleurs.add(violet);


        for (final Couleur c : couleurs) {
            c.getImageView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    couleurChoisieCreationPartie = c.getNom();
                    selectedColorCreationPartie.setX(c.getImageView().getX() + (c.getImageView().getLayoutParams().width / 4));
                    selectedColorCreationPartie.setY(c.getImageView().getY() + (c.getImageView().getLayoutParams().height / 4));

                }
            });
        }

        backToPlayPopupCreationPartie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                creationPartiePopup.cancel();
            }
        });

        goToSalonCreation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Menu.client.couleur > -1){
                    creationPartiePopup.cancel();
                }
                if (couleurChoisieCreationPartie.equals("")) {
                    Toast.makeText(getBaseContext(), "Veuillez entrer un nom et choisir une couleur", Toast.LENGTH_LONG).show();
                } else {
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            switch (couleurChoisieCreationPartie){
                                case "rouge":
                                    Menu.client.color(0); break;
                                case "vert":
                                    Menu.client.color(1); break;
                                case "bleu":
                                    Menu.client.color(2); break;
                                case "violet":
                                    Menu.client.color(3); break;
                            }
                        }
                    });
                    thread.start();
                    thread.interrupt();
                }
            }
        });

        creationPartiePopup.getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        creationPartiePopup.show();
        setCreationPartiePopupComponentsSize();


        backToMenuSalonRejoindrePartie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonRejoindrePartie.this, Menu.class));
            }
        });

        pret_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Menu.client.ready();
                    }
                });
                thread.start();
                thread.interrupt();
            }
        });
    }

    public void setCreationPartiePopupComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        Integer newWidth;
        Integer newHeight;
        Double coef;

        cadreCreationPartie.getLayoutParams().width = (int) (displayWidth / 1.5);
        cadreCreationPartie.getLayoutParams().height = (int)  (displayHeight / 2.3);

        /*coef = (double) 291/72;
        texteNomCreationPartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
        newHeight = (int)  ((displayHeight / 5) / 3);
        texteNomCreationPartie.getLayoutParams().width = (int) (newHeight * coef);*/

        coef = (double) 462/72;
        texteCouleurCreationPartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
        newHeight = (int)  ((displayHeight / 5) / 3);
        texteCouleurCreationPartie.getLayoutParams().width = (int) (newHeight * coef);

        coef = (double) 628/415;
        backToPlayPopupCreationPartie_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        backToPlayPopupCreationPartie_btn.getLayoutParams().height = (int)  (newWidth / coef);

        coef = (double) 628/415;
        goToSalonCreation_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        goToSalonCreation_btn.getLayoutParams().height = (int)  (newWidth / coef);

        redCreationPartie.getLayoutParams().width = displayWidth / 16;
        redCreationPartie.getLayoutParams().height = displayWidth / 16;

        greenCreationPartie.getLayoutParams().width = displayWidth / 16;
        greenCreationPartie.getLayoutParams().height = displayWidth / 16;

        blueCreationPartie.getLayoutParams().width = displayWidth / 16;
        blueCreationPartie.getLayoutParams().height = displayWidth / 16;

        purpleCreationPartie.getLayoutParams().width = displayWidth / 16;
        purpleCreationPartie.getLayoutParams().height = displayWidth / 16;

        /*for (int i =0; i < Server.colorsAvailable.length; i++)
            if (!Server.colorsAvailable[i])
                switch (i){
                    case 0:
                        redCreationPartie.setEnabled(false); break;
                    case 1:
                        redCreationPartie.setEnabled(false); break;
                    case 2:
                        redCreationPartie.setEnabled(false); break;
                    case 3:
                        redCreationPartie.setEnabled(false); break;
                    default:
                        break;
                }*/

        selectedColorCreationPartie.getLayoutParams().width = displayWidth / 32;
        selectedColorCreationPartie.getLayoutParams().height = displayWidth / 32;
        selectedColorCreationPartie.setX(displayWidth * 2);

    }

    private void setComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        Integer newWidth;
        Integer newHeight;
        Double coef;


        coef = (double) 628/415;
        backToMenuSalonRejoindrePartie_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        backToMenuSalonRejoindrePartie_btn.getLayoutParams().height = (int)  (newWidth / coef);
        backToMenuSalonRejoindrePartie_btn.setX(percentWidth(10, backToMenuSalonRejoindrePartie_btn));
        backToMenuSalonRejoindrePartie_btn.setY(percentHeight(15, backToMenuSalonRejoindrePartie_btn));

        coef = (double) 700/180;
        pret_btn.getLayoutParams().width = displayWidth / 2;
        newWidth = displayWidth / 2;
        pret_btn.getLayoutParams().height = (int)  ((newWidth / coef) / 1.3);
        pret_btn.setX(percentWidth(40, pret_btn));
        pret_btn.setY(percentHeight(85,pret_btn));

        coef = (double)  cadreJoueurs.getHeight() / cadreJoueurs.getWidth();
        cadreJoueurs.getLayoutParams().height = (int) (displayHeight / 1.1);
        newHeight = cadreJoueurs.getLayoutParams().height;
        cadreJoueurs.getLayoutParams().width = (int)  (newHeight * coef);
        cadreJoueurs.setX(percentWidth(80, cadreJoueurs));
        cadreJoueurs.setY(percentHeight(50, cadreJoueurs));

       /* coef = (double) bande.getWidth() / (bande.getHeight() / 3);
        bande.getLayoutParams().width = (int) (displayWidth / 1.5);
        newWidth = bande.getLayoutParams().width;
        bande.getLayoutParams().height = (int)  (newWidth / coef);
        bande.setX(percentWidth(40, bande));
        bande.setY(percentHeight(50, bande));*/


    }

    private int percentWidth(Integer percent, View v) {
        int navigationBarHeight = 0;
        int resourceId = getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            navigationBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return (((getWindowManager().getDefaultDisplay().getWidth() + navigationBarHeight) * percent) / 100)  - (v.getLayoutParams().width / 2);
    }

    private int percentHeight(Integer percent, View v) {
        int statusBarHeight = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getResources().getDimensionPixelSize(resourceId);
        }
        return (((getWindowManager().getDefaultDisplay().getHeight() ) * percent) / 100)  - (v.getLayoutParams().height / 2);

    }

    public static void afficherJoueurHote(String pseudo, int color){
        int c = -1;
        switch(color){
            case 0:
                c = Color.RED; break;
            case 1:
                c = Color.GREEN; break;
            case 2:
                c = Color.BLUE; break;
            case 3:
                c = Color.BLACK; break;
        }
        nomJoueur1.setText("" + pseudo);
        nomJoueur1.setTextColor(c);
    }

    public static void afficherJoueur(String pseudo, int player, int color){
        int c = -1;
        switch(color){
            case 0:
                c = Color.RED; break;
            case 1:
                c = Color.GREEN; break;
            case 2:
                c = Color.BLUE; break;
            case 3:
                c = Color.BLACK; break;
        }

        if (c != -1) {
            switch (player) {
                case 1:
                    nomJoueur2.setText("" + pseudo);
                    nomJoueur2.setTextColor(c);
                    break;
                case 2:
                    nomJoueur3.setText("" + pseudo);
                    nomJoueur3.setTextColor(c);
                    break;
                case 3:
                    nomJoueur4.setText("" + pseudo);
                    nomJoueur4.setTextColor(c);
                    break;
            }
        }
    }

    public static void lancerJeu() {
       Intent intent = new Intent(context, EnqueteActivity.class);
       context.startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (creationPartiePopup != null) {
            creationPartiePopup.dismiss();
            creationPartiePopup = null;
        }
    }
}
