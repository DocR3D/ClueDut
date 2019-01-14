package com.example.maxime.testenvoie;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.maxime.testenvoie.classes.Case;
import com.example.maxime.testenvoie.classes.CaseJoueur;
import com.example.maxime.testenvoie.classes.Joueur;
import com.example.maxime.testenvoie.classes.Salle;

import java.util.ArrayList;
import java.util.List;

public class JouerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static Case[][] plateau;
    public static RelativeLayout fenetreSecondaire;
    private LinearLayout linearLayout;
    private float x;
    private float y;
    private int dim;
    public static int deplacement;
    private ImageButton imCase;
    private List<ImageButton> listImCase;
    private List<Case> listCase;
    private Case pCase;
    public static Joueur j1;
    public static Joueur j2;
    public static Joueur j3;
    public static Joueur j4;
    public static List<Salle> listSalles;
    private Button de;
    private Button ficheEnquete;
    private DrawerLayout drawer;
    public static JouerActivity context;
    public Dialog choixAction;
    public Dialog action;
    public Dialog fichepopup;
    public Button hypothèse;
    public Button accusation;
    public static CheckBox colonnelMoutarde;
    public static CheckBox reverandOlive;
    public static CheckBox professeurViolet;
    public static CheckBox madamePervenche;
    public static CheckBox mademoiselleRose;
    public static CheckBox madameLeBlanc;

    public static CheckBox poignard;
    public static CheckBox revolver;
    public static CheckBox corde;
    public static CheckBox chandelier;
    public static CheckBox matraque;
    public static CheckBox cleAnglaise;

    public static CheckBox salleDeBillard;
    public static CheckBox salleDeBal;
    public static CheckBox cuisine;
    public static CheckBox veranda;
    public static CheckBox hall;
    public static CheckBox salleAManger;
    public static CheckBox bibliotheque;
    public static CheckBox salon;
    public static CheckBox bureau;

    public Joueur joueur;

    public static CheckBox[] checkBoxs = new CheckBox[21];

    public Button valider;

    public boolean firstFocus = true;

    public DisplayMetrics metrics;

    public Button retour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        context = this;

        /*Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open , R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();*/

        linearLayout = (LinearLayout) findViewById(R.id.fenetrePrincipale);
        fenetreSecondaire = (RelativeLayout) findViewById(R.id.fenetreSecondaire);
        de = (Button) findViewById(R.id.de);
        ficheEnquete = (Button) findViewById(R.id.ficheEnquete);

        listImCase = new ArrayList<>();
        listCase = new ArrayList<>();
        listSalles = new ArrayList<>();
        plateau = new Case[25][25];
        j1 = new Joueur();
        j2 = new Joueur();
        j3 = new Joueur();
        j4 = new Joueur();

        switch (Menu.client.getNumJoueur()) {
            case 0:
                Menu.client.setJoueur(j1);
                break;
            case 1:
                Menu.client.setJoueur(j2);
                break;
            case 2:
                Menu.client.setJoueur(j3);
                break;
            case 3:
                Menu.client.setJoueur(j4);
                break;
        }

        joueur = Menu.client.getJoueur();

        metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        dim = (metrics.heightPixels / (plateau.length - 1));

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
            creationPlateau(metrics);
            firstFocus = false;
        }
    }

    public void creationPlateau(DisplayMetrics metrics) {

        de.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Menu.client.dice();
                    }
                });
                thread.start();
                thread.interrupt();
            }
        });

        ficheEnquete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fichepopup = new Dialog(JouerActivity.this);
                fichepopup.setContentView(R.layout.activity_fiche_enquete);
                retour = fichepopup.findViewById(R.id.retour);
                CheckBox[] tempCheckBoxs = new CheckBox[21];
                colonnelMoutarde = (CheckBox) fichepopup.findViewById(R.id.ColonelMoutarde);
                reverandOlive = (CheckBox) fichepopup.findViewById(R.id.ReverandOlive);
                professeurViolet = (CheckBox) fichepopup.findViewById(R.id.ProfesseurViolet);
                madameLeBlanc = (CheckBox) fichepopup.findViewById(R.id.MadameLeblanc);
                madamePervenche = (CheckBox) fichepopup.findViewById(R.id.MadamePervenche);
                mademoiselleRose = (CheckBox) fichepopup.findViewById(R.id.MademoiselleRose);

                poignard = (CheckBox) fichepopup.findViewById(R.id.poignard);
                chandelier = (CheckBox) fichepopup.findViewById(R.id.chandelier);
                corde = (CheckBox) fichepopup.findViewById(R.id.corde);
                cleAnglaise = (CheckBox) fichepopup.findViewById(R.id.cleAnglaise);
                revolver = (CheckBox) fichepopup.findViewById(R.id.Revolver);
                matraque = (CheckBox) fichepopup.findViewById(R.id.matraque);

                salleAManger = (CheckBox) fichepopup.findViewById(R.id.salleAManger);
                salleDeBal = (CheckBox) fichepopup.findViewById(R.id.salleDeBal);
                salleDeBillard = (CheckBox) fichepopup.findViewById(R.id.billard);
                salon = (CheckBox) fichepopup.findViewById(R.id.salon);
                hall = (CheckBox) fichepopup.findViewById(R.id.hall);
                bureau = (CheckBox) fichepopup.findViewById(R.id.bureau);
                bibliotheque = (CheckBox) fichepopup.findViewById(R.id.bibliotheque);
                veranda = (CheckBox) fichepopup.findViewById(R.id.verenda);
                cuisine = (CheckBox) fichepopup.findViewById(R.id.Cuisine);

                tempCheckBoxs[0] = colonnelMoutarde;
                tempCheckBoxs[1] = professeurViolet;
                tempCheckBoxs[2] = reverandOlive;
                tempCheckBoxs[3] = mademoiselleRose;
                tempCheckBoxs[4] = madamePervenche;
                tempCheckBoxs[5] = madameLeBlanc;

                tempCheckBoxs[6] = poignard;
                tempCheckBoxs[7] = cleAnglaise;
                tempCheckBoxs[8] = corde;
                tempCheckBoxs[9] = chandelier;
                tempCheckBoxs[10] = revolver;
                tempCheckBoxs[11] = matraque;

                tempCheckBoxs[12] = salleDeBillard;
                tempCheckBoxs[13] = salleAManger;
                tempCheckBoxs[14] = salleDeBal;
                tempCheckBoxs[15] = salon;
                tempCheckBoxs[16] = bureau;
                tempCheckBoxs[17] = bibliotheque;
                tempCheckBoxs[18] = hall;
                tempCheckBoxs[19] = cuisine;
                tempCheckBoxs[20] = veranda;

                for (int i = 0; i < EnqueteActivity.checkBoxs.length; i++){
                    for (int j = 0; j < tempCheckBoxs.length; j++){
                        if (EnqueteActivity.checkBoxs[i].getText().equals(tempCheckBoxs[j].getText())){
                            if (EnqueteActivity.checkBoxs[i].isChecked()){
                                tempCheckBoxs[j].setChecked(true);
                            }
                        }
                    }
                }


                fichepopup.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                fichepopup.show();

                retour.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        fichepopup.cancel();
                    }
                });
            }
        });

        x = ((metrics.widthPixels - plateau.length * dim) / 2) - 100;

        for (int i = 0; i < plateau.length; i++) {
            for (int j = 0; j < plateau.length; j++) {
                if ((i == 9 && j == 0) || (i == 0 && j == 8) || (i == 7 && j == 23) || (i == 24 && j ==7)) {
                    creerCaseJoueur(dim, x, y, i, j);
                    y += dim;
                } else {
                    creerCase(dim, x, y, i, j);
                    y += dim;
                }
            }
            x += dim;
            y = 0;
        }

        x = ((metrics.widthPixels - plateau.length * dim) / 2) - 100;


        ImageView imageCuisine = new ImageButton(this);
        imageCuisine.setBackgroundResource(R.drawable.cuisine);

        afficherSalle(imageCuisine, 6, 6, 0, 1);


        ImageView imageManger1 = new ImageButton(this);
        imageManger1.setBackgroundResource(R.drawable.sallemanger1);

        afficherSalle(imageManger1, 5, 1, 0, 9);


        ImageButton imageManger2 = new ImageButton(this);
        imageManger2.setBackgroundResource(R.drawable.sallemanger2);

        afficherSalle(imageManger2, 8, 6, 0, 10);


        ImageButton imageSalon1 = new ImageButton(this);
        imageSalon1.setBackgroundResource(R.drawable.salon1);

        afficherSalle(imageSalon1, 1, 5, 6, 19);


        ImageButton imageSalon2 = new ImageButton(this);
        imageSalon2.setBackgroundResource(R.drawable.salon2);

        afficherSalle(imageSalon2, 6, 6, 0, 19);


        ImageButton imageCluedo = new ImageButton(this);
        imageCluedo.setBackgroundResource(R.drawable.cluedo);

        afficherSalle(imageCluedo, 5, 7, 10, 10);

        ImageButton imageHall = new ImageButton(this);
        imageHall.setBackgroundResource(R.drawable.hall);

        afficherSalle(imageHall, 6, 7, 9, 18);


        ImageButton imageBillard = new ImageButton(this);
        imageBillard.setBackgroundResource(R.drawable.sallebillard);

        afficherSalle(imageBillard, 6, 5, 18, 8);

        ImageButton imageBureau = new ImageButton(this);
        imageBureau.setBackgroundResource(R.drawable.bureau);

        afficherSalle(imageBureau, 7, 5, 17, 20);

        ImageButton imageBal1 = new ImageButton(this);
        imageBal1.setBackgroundResource(R.drawable.sallebal1);

        afficherSalle(imageBal1, 8, 6, 8, 2);

        ImageButton imageBal2 = new ImageButton(this);
        imageBal2.setBackgroundResource(R.drawable.sallebal2);

        afficherSalle(imageBal2, 4, 1, 10, 1);

        ImageButton imageVeranda1 = new ImageButton(this);
        imageVeranda1.setBackgroundResource(R.drawable.veranda1);

        afficherSalle(imageVeranda1, 6, 4, 18, 1);

        ImageButton imageVeranda2 = new ImageButton(this);
        imageVeranda2.setBackgroundResource(R.drawable.veranda2);

        afficherSalle(imageVeranda2, 4, 1, 19, 5);


        ImageButton imageBibliotheque1 = new ImageButton(this);
        imageBibliotheque1.setBackgroundResource(R.drawable.bibliotheque1);

        afficherSalle(imageBibliotheque1, 5, 5, 18, 14);


        ImageButton imageBibliotheque2 = new ImageButton(this);
        imageBibliotheque2.setBackgroundResource(R.drawable.bibliotheque2);

        afficherSalle(imageBibliotheque2, 1, 3, 17, 15);

        ImageButton imageBibliotheque3 = new ImageButton(this);
        imageBibliotheque3.setBackgroundResource(R.drawable.bibliotheque3);

        afficherSalle(imageBibliotheque3, 1, 3, 23, 15);


        Salle salleCuisine = new Salle("Cuisine", plateau[4][7]);
        listSalles.add(salleCuisine);

        afficherPorteSalle(salleCuisine, 4, 7);

        Salle salleBal = new Salle("Salle de bal", plateau[9][8]);
        listSalles.add(salleBal);

        afficherPorteSalle(salleBal, 9, 8);

        Salle salleManger = new Salle("Salle à manger", plateau[6][16]);
        listSalles.add(salleManger);

        afficherPorteSalle(salleManger, 6, 16);

        Salle salleSalon = new Salle("Salon", plateau[6][18]);
        listSalles.add(salleSalon);

        afficherPorteSalle(salleSalon, 6, 18);

        Salle salleHall = new Salle("Hall", plateau[12][17]);
        listSalles.add(salleHall);

        afficherPorteSalle(salleHall, 12, 17);

        Salle salleBureau = new Salle("Bureau", plateau[17][19]);
        listSalles.add(salleBureau);

        afficherPorteSalle(salleBureau, 17, 19);

        Salle salleBibliotheque = new Salle("Bibliotheque", plateau[16][16]);
        listSalles.add(salleBibliotheque);

        afficherPorteSalle(salleBibliotheque, 16, 16);

        Salle salleBillard = new Salle("Salle de billard", plateau[17][9]);
        listSalles.add(salleBillard);

        afficherPorteSalle(salleBillard, 17, 9);

        Salle salleVeranda = new Salle("Veranda", plateau[18][5]);
        listSalles.add(salleVeranda);

        afficherPorteSalle(salleVeranda, 18, 5);

    }

    private void afficherPorteSalle(final Salle salle, final int posX, final int posY) {

        ImageButton imageSalle = new ImageButton(this);
        imageSalle.setBackgroundResource(R.drawable.clickcase);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dim, dim);
        imageSalle.setLayoutParams(params);

        imageSalle.setX(x + posX * dim);
        imageSalle.setY(posY * dim);

        fenetreSecondaire.addView(imageSalle);

        imageSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (salle.getCase().getClickable()) {
                    joueur.changerCaseJoueur(salle.getCase(), posX, posY, Menu.client.getNumJoueur());
                    choixAction = new Dialog(JouerActivity.this);
                    choixAction.setContentView(R.layout.popup_choix_action);
                    hypothèse = choixAction.findViewById(R.id.Hypothèse);
                    accusation = choixAction.findViewById(R.id.Accusation);

                    choixAction.getWindow().getDecorView().setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                    choixAction.show();

                    hypothèse.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String[] personnage = {null};
                            final String[] arme = {null};
                            final String lieu = salle.getNom();
                            action = new Dialog(JouerActivity.this);
                            action.setContentView(R.layout.popup_action1);
                            valider = action.findViewById(R.id.valider);
                            colonnelMoutarde = (CheckBox) action.findViewById(R.id.ColonelMoutarde);
                            reverandOlive = (CheckBox) action.findViewById(R.id.ReverandOlive);
                            professeurViolet = (CheckBox) action.findViewById(R.id.ProfesseurViolet);
                            madameLeBlanc = (CheckBox) action.findViewById(R.id.MadameLeblanc);
                            madamePervenche = (CheckBox) action.findViewById(R.id.MadamePervenche);
                            mademoiselleRose = (CheckBox) action.findViewById(R.id.MademoiselleRose);

                            poignard = (CheckBox) action.findViewById(R.id.poignard);
                            chandelier = (CheckBox) action.findViewById(R.id.chandelier);
                            corde = (CheckBox) action.findViewById(R.id.corde);
                            cleAnglaise = (CheckBox) action.findViewById(R.id.cleAnglaise);
                            revolver = (CheckBox) action.findViewById(R.id.Revolver);
                            matraque = (CheckBox) action.findViewById(R.id.matraque);

                            checkBoxs[0] = colonnelMoutarde;
                            checkBoxs[1] = professeurViolet;
                            checkBoxs[2] = reverandOlive;
                            checkBoxs[3] = mademoiselleRose;
                            checkBoxs[4] = madamePervenche;
                            checkBoxs[5] = madameLeBlanc;

                            checkBoxs[6] = poignard;
                            checkBoxs[7] = cleAnglaise;
                            checkBoxs[8] = corde;
                            checkBoxs[9] = chandelier;
                            checkBoxs[10] = revolver;
                            checkBoxs[11] = matraque;

                            action.getWindow().getDecorView().setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                            action.show();

                            for (int i = 0; i < 6; i++){
                                final int finalI = i;
                                checkBoxs[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkBoxs[finalI].isChecked()) {
                                            personnage[0] = (String) checkBoxs[finalI].getText();
                                            checkBoxs[finalI].setChecked(true);
                                            dechecker((String)checkBoxs[finalI].getText(), 0, 5);
                                        }
                                        else {
                                            personnage[0] = null;
                                            checker((String) checkBoxs[finalI].getText(), 0, 5);
                                            checkBoxs[finalI].setChecked(false);
                                        }
                                    }
                                });
                            }

                            for (int i = 6; i < 12; i++){
                                final int finalI = i;
                                checkBoxs[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkBoxs[finalI].isChecked()) {
                                            arme[0] = (String) checkBoxs[finalI].getText();
                                            checkBoxs[finalI].setChecked(true);
                                            dechecker((String) checkBoxs[finalI].getText(), 6, 11);
                                        }
                                        else {
                                            arme[0] = null;
                                            checker((String) checkBoxs[finalI].getText(), 6, 11);
                                            checkBoxs[finalI].setChecked(false);
                                        }
                                    }
                                });
                            }

                            valider.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (personnage[0] != null && arme[0] != null){
                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Menu.client.hyp(personnage[0], arme[0], lieu);
                                                action.cancel();
                                                choixAction.cancel();
                                            }
                                        });
                                        thread.start();
                                        thread.interrupt();
                                    }
                                }
                            });

                        }
                    });

                    accusation.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final String[] personnage = {null};
                            final String[] arme = {null};
                            final String lieu = salle.getNom();
                            action = new Dialog(JouerActivity.this);
                            action.setContentView(R.layout.popup_action1);
                            valider = action.findViewById(R.id.valider);
                            colonnelMoutarde = (CheckBox) action.findViewById(R.id.ColonelMoutarde);
                            reverandOlive = (CheckBox) action.findViewById(R.id.ReverandOlive);
                            professeurViolet = (CheckBox) action.findViewById(R.id.ProfesseurViolet);
                            madameLeBlanc = (CheckBox) action.findViewById(R.id.MadameLeblanc);
                            madamePervenche = (CheckBox) action.findViewById(R.id.MadamePervenche);
                            mademoiselleRose = (CheckBox) action.findViewById(R.id.MademoiselleRose);

                            poignard = (CheckBox) action.findViewById(R.id.poignard);
                            chandelier = (CheckBox) action.findViewById(R.id.chandelier);
                            corde = (CheckBox) action.findViewById(R.id.corde);
                            cleAnglaise = (CheckBox) action.findViewById(R.id.cleAnglaise);
                            revolver = (CheckBox) action.findViewById(R.id.Revolver);
                            matraque = (CheckBox) action.findViewById(R.id.matraque);

                            checkBoxs[0] = colonnelMoutarde;
                            checkBoxs[1] = professeurViolet;
                            checkBoxs[2] = reverandOlive;
                            checkBoxs[3] = mademoiselleRose;
                            checkBoxs[4] = madamePervenche;
                            checkBoxs[5] = madameLeBlanc;

                            checkBoxs[6] = poignard;
                            checkBoxs[7] = cleAnglaise;
                            checkBoxs[8] = corde;
                            checkBoxs[9] = chandelier;
                            checkBoxs[10] = revolver;
                            checkBoxs[11] = matraque;

                            action.getWindow().getDecorView().setSystemUiVisibility(
                                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                            action.show();

                            for (int i = 0; i < 6; i++){
                                final int finalI = i;
                                checkBoxs[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkBoxs[finalI].isChecked()) {
                                            personnage[0] = (String) checkBoxs[finalI].getText();
                                            System.out.println(personnage[0]);
                                            checkBoxs[finalI].setChecked(true);
                                            dechecker((String)checkBoxs[finalI].getText(), 0, 5);
                                        }
                                        else {
                                            personnage[0] = null;
                                            System.out.println(personnage[0]);
                                            checker((String) checkBoxs[finalI].getText(), 0, 5);
                                            checkBoxs[finalI].setChecked(false);
                                        }
                                    }
                                });
                            }

                            for (int i = 6; i < 12; i++){
                                final int finalI = i;
                                checkBoxs[i].setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkBoxs[finalI].isChecked()) {
                                            arme[0] = (String) checkBoxs[finalI].getText();
                                            System.out.println(arme[0]);
                                            checkBoxs[finalI].setChecked(true);
                                            dechecker((String) checkBoxs[finalI].getText(), 6, 11);
                                        }
                                        else {
                                            arme[0] = null;
                                            System.out.println(arme[0]);
                                            checker((String) checkBoxs[finalI].getText(), 6, 11);
                                            checkBoxs[finalI].setChecked(false);
                                        }
                                    }
                                });
                            }

                            valider.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (personnage[0] != null && arme[0] != null){
                                        Thread thread = new Thread(new Runnable() {
                                            @Override
                                            public void run() {
                                                Menu.client.acs(personnage[0], arme[0], lieu);
                                            }
                                        });
                                        thread.start();
                                        thread.interrupt();
                                        action.cancel();
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
    }

    private void afficherSalle(ImageView image, int tailleXImage, int tailleYImage, int posX, int posY) {

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(tailleXImage * dim, tailleYImage * dim);
        image.setLayoutParams(params);

        image.setX(x + posX * dim);
        image.setY(posY * dim);

        fenetreSecondaire.addView(image);
    }

    public void creerCaseJoueur(int pDim, float pX, float pY, int i, int j) {
        Joueur pJ = null;
        int numJoueur = -1;
        String temp = "" + i + j;
        switch (temp) {
            case "90":
                pJ = j1;
                numJoueur = 0;
                break;
            case "08":
                pJ = j2;
                numJoueur = 1;
                break;
            case "723":
                pJ = j3;
                numJoueur = 2;
                break;
            case "247":
                pJ = j4;
                numJoueur = 3;
                break;
        }


        imCase = new ImageButton(this);
        pCase = new CaseJoueur(imCase, pJ);

        plateau[i][j] = pCase;

        pJ.setCaseJ(pCase);
        switch (Menu.client.getCouleur(numJoueur)) {
            case 0:
                imCase.setBackgroundResource(R.drawable.pawnrouge);
                break;
            case 1:
                imCase.setBackgroundResource(R.drawable.pawnvert);
                break;
            case 2:
                imCase.setBackgroundResource(R.drawable.pawnbleu);
                break;
            case 3:
                imCase.setBackgroundResource(R.drawable.pawnviolet);
                break;
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(pDim, pDim);
        imCase.setLayoutParams(params);

        imCase.setX(pX);
        imCase.setY(pY);

        fenetreSecondaire.addView(imCase);
        listImCase.add(imCase);
    }

    public void creerCase(int pDim, float pX, float pY, int i, int j) {
        imCase = new ImageButton(this);
        pCase = new Case(imCase);
        plateau[i][j] = pCase;
        placerImage(imCase, pDim, pX, pY, pCase);
    }

    public void placerImage(final ImageButton imCase, int pDim, float pX, float pY, final Case pCase) {
        imCase.setBackgroundResource(R.drawable.fondcase);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(pDim, pDim);
        imCase.setLayoutParams(params);

        imCase.setX(pX);
        imCase.setY(pY);

        fenetreSecondaire.addView(imCase);
        listImCase.add(imCase);

        imCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int posI = 0;
                int posJ = 0;
                if (pCase.getClickable()) {
                    imCase.setBackgroundResource(R.drawable.pawnrouge);
                    for (int i = 0; i < plateau.length; i ++){
                        for (int j = 0; j < plateau.length; j ++){
                            if (pCase.equals(plateau[i][j])){
                                posI = i;
                                posJ = j;
                            }
                        }
                    }
                    joueur.changerCaseJoueur(pCase, posI, posJ, Menu.client.getNumJoueur());
                }
            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void dechecker(String nomBox, int borneInf, int borneSup){
        for (int i = borneInf; i <= borneSup; i++){
            if (!checkBoxs[i].getText().equals(nomBox)){
                checkBoxs[i].setEnabled(false);
            }
        }
    }

    private void checker(String nomBox, int borneInf, int borneSup){
        for (int i = borneInf; i <= borneSup; i++){
            if (!checkBoxs[i].getText().equals(nomBox)){
                checkBoxs[i].setEnabled(true);
            }
        }
    }

    public static void moveJoueur(int numJoueur, int i, int j){
        System.out.println("Je suis passé ici");
        switch (numJoueur) {
            case 0:
                System.out.println("Je suis passé ici");
                j1.setCaseJ(plateau[i][j]);
                break;
            case 1:
                System.out.println("Je suis passé ici");
                j2.setCaseJ(plateau[i][j]);
                break;
            case 2:
                System.out.println("Je suis passé ici");
                j3.setCaseJ(plateau[i][j]);
                break;
            case 3:
                System.out.println("Je suis passé ici");
                j4.setCaseJ(plateau[i][j]);
                break;
        }
        Joueur.afficherAutreJoueur(numJoueur);
    }

    public static void finWin(){
        Intent intent = new Intent(context, FinActivityWin.class);
        context.startActivity(intent);
    }

    public static void finLose(){
        Intent intent = new Intent(context, FinActivityLose.class);
        context.startActivity(intent);
    }

    public static void affecterJoueur(){

    }
}
