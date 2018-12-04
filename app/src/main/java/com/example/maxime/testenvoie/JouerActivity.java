package com.example.maxime.testenvoie;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JouerActivity extends AppCompatActivity {

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
    private Joueur j1;
    public static List<Salle> listSalles;
    private Button boutonDe;
    private ImageView de;
    Bitmap myBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jouer);

        linearLayout = (LinearLayout) findViewById(R.id.fenetrePrincipale);
        fenetreSecondaire = (RelativeLayout) findViewById(R.id.fenetreSecondaire);
        de = (ImageView) findViewById(R.id.de);
        boutonDe = (Button) findViewById(R.id.lancerDe);

        listImCase = new ArrayList<>();
        listCase = new ArrayList<>();
        listSalles = new ArrayList<>();
        plateau = new Case[25][25];

        boutonDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int chiffre = (int) ((Math.random() * 6) +1);
                linearLayout.removeView(de);
                switch(chiffre){
                    case 1 :
                        de.setBackgroundResource(R.drawable.de1); break;
                    case 2 :
                        de.setBackgroundResource(R.drawable.de2); break;
                    case 3 :
                        de.setBackgroundResource(R.drawable.de3); break;
                    case 4 :
                        de.setBackgroundResource(R.drawable.de4); break;
                    case 5 :
                        de.setBackgroundResource(R.drawable.de5); break;
                    case 6 :
                        de.setBackgroundResource(R.drawable.de6); break;
                }
                linearLayout.addView(de);
                deplacement = (chiffre);
                j1.deplacementJoueur();
            }
        });

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        dim = (metrics.heightPixels / (plateau.length - 1));

        creationPlateau(metrics);
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
    }

    public void creationPlateau(DisplayMetrics metrics){

        j1 = new Joueur();

        x = ((metrics.widthPixels - plateau.length * dim) / 2) - 100;

        for (int i = 0; i < plateau.length; i++){
            for (int j = 0; j < plateau.length; j++){
                if (i == 9 && j == 0){
                    creerCaseJoueur(dim,x,y,i,j,j1);
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
        //File imgFile = new  File("sdcard/map/cuisine.png");
        Bitmap bmp = BitmapFactory.decodeFile("sdcard/map/cuisine.png");
        imageCuisine.setImageBitmap(bmp);
        /*if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageCuisine.setImageBitmap(myBitmap);
        }*/
        //imageCuisine.setBackgroundResource(R.drawable.cuisine);

        afficherSalle(imageCuisine, 6,6, 0, 1 );


        ImageView imageManger1 = new ImageButton(this);
        File imgFile = new  File("sdcard/map/sallemanger1.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageManger1.setImageBitmap(myBitmap);
        }
        //imageManger1.setBackgroundResource(R.drawable.sallemanger1);

        afficherSalle(imageManger1, 5,1, 0, 9 );


        ImageButton imageManger2 = new ImageButton(this);
        imgFile = new  File("sdcard/map/sallemanger2.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageManger2.setImageBitmap(myBitmap);
        }
        //imageManger2.setBackgroundResource(R.drawable.sallemanger2);

        afficherSalle(imageManger2, 8,6, 0, 10 );


        ImageButton imageSalon1 = new ImageButton(this);
        imgFile = new  File("sdcard/map/salon1.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageSalon1.setImageBitmap(myBitmap);
        }
        //imageSalon1.setBackgroundResource(R.drawable.salon1);

        afficherSalle(imageSalon1, 1,5, 6, 19 );


        ImageButton imageSalon2 = new ImageButton(this);
        imgFile = new  File("sdcard/map/salon2.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageSalon2.setImageBitmap(myBitmap);
        }
        //imageSalon2.setBackgroundResource(R.drawable.salon2);

        afficherSalle(imageSalon2, 6,6, 0, 19 );


        ImageButton imageCluedo = new ImageButton(this);
        imgFile = new  File("sdcard/map/cluedo.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageCluedo.setImageBitmap(myBitmap);
        }
        //imageCluedo.setBackgroundResource(R.drawable.cluedo);

        afficherSalle(imageCluedo, 5,7, 10, 10 );

        ImageButton imageHall = new ImageButton(this);
        imgFile = new  File("sdcard/map/hall.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageHall.setImageBitmap(myBitmap);
        }
        //imageHall.setBackgroundResource(R.drawable.hall);

        afficherSalle(imageHall, 6,7, 9, 18 );


        ImageButton imageBillard = new ImageButton(this);
        imgFile = new  File("sdcard/map/sallebillard.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBillard.setImageBitmap(myBitmap);
        }
        //imageBillard.setBackgroundResource(R.drawable.sallebillard);

        afficherSalle(imageBillard, 6,5, 18, 8 );

        ImageButton imageBureau = new ImageButton(this);
        imgFile = new  File("sdcard/map/bureau.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBureau.setImageBitmap(myBitmap);
        }
        //imageBureau.setBackgroundResource(R.drawable.bureau);

        afficherSalle(imageBureau, 7,5, 17, 20 );

        ImageButton imageBal1 = new ImageButton(this);
        imgFile = new  File("sdcard/map/sallebal1.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBal1.setImageBitmap(myBitmap);
        }
        //imageBal1.setBackgroundResource(R.drawable.sallebal1);

        afficherSalle(imageBal1, 8,6, 8, 2 );

        ImageButton imageBal2 = new ImageButton(this);
        imgFile = new  File("sdcard/map/sallebal2.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBal2.setImageBitmap(myBitmap);
        }
        //imageBal2.setBackgroundResource(R.drawable.sallebal2);

        afficherSalle(imageBal2, 4,1, 10, 1 );

        ImageButton imageVeranda1 = new ImageButton(this);
        imgFile = new  File("sdcard/map/veranda1.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageVeranda1.setImageBitmap(myBitmap);
        }
        //imageVeranda1.setBackgroundResource(R.drawable.veranda1);

        afficherSalle(imageVeranda1, 6,4, 18, 1 );

        ImageButton imageVeranda2 = new ImageButton(this);
        imgFile = new  File("sdcard/map/veranda2.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageVeranda2.setImageBitmap(myBitmap);
        }
        //imageVeranda2.setBackgroundResource(R.drawable.veranda2);

        afficherSalle(imageVeranda2, 4,1, 19, 5 );


        ImageButton imageBibliotheque1 = new ImageButton(this);
        imgFile = new  File("sdcard/map/bibliotheque1.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBibliotheque1.setImageBitmap(myBitmap);
        }
        //imageBibliotheque1.setBackgroundResource(R.drawable.bibliotheque1);

        afficherSalle(imageBibliotheque1, 5,5, 18, 14 );


        ImageButton imageBibliotheque2 = new ImageButton(this);
        imgFile = new  File("sdcard/map/bibliotheque2.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBibliotheque2.setImageBitmap(myBitmap);
        }
        //imageBibliotheque2.setBackgroundResource(R.drawable.bibliotheque2);

        afficherSalle(imageBibliotheque2, 1,3, 17, 15 );

        ImageButton imageBibliotheque3 = new ImageButton(this);
        imgFile = new  File("sdcard/map/bibliotheque3.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageBibliotheque3.setImageBitmap(myBitmap);
        }
        //imageBibliotheque3.setBackgroundResource(R.drawable.bibliotheque3);

        afficherSalle(imageBibliotheque3, 1,3, 23, 15 );


        Salle salleCuisine = new Salle("Cuisine", plateau[4][7]);
        listSalles.add(salleCuisine);

        afficherPorteSalle(salleCuisine,4,7);

        Salle salleBal = new Salle("Salle de bal", plateau[9][8]);
        listSalles.add(salleBal);

        afficherPorteSalle(salleBal,9,8);

        Salle salleManger = new Salle("Salle à manger", plateau[6][16]);
        listSalles.add(salleManger);

        afficherPorteSalle(salleManger,6,16);

        Salle salleSalon = new Salle("Salon", plateau[6][18]);
        listSalles.add(salleSalon);

        afficherPorteSalle(salleSalon,6,18);

        Salle salleHall = new Salle("Hall", plateau[12][17]);
        listSalles.add(salleHall);

        afficherPorteSalle(salleHall,12,17);

        Salle salleBureau = new Salle("Bureau", plateau[17][19]);
        listSalles.add(salleBureau);

        afficherPorteSalle(salleBureau,17,19);

        Salle salleBibliotheque = new Salle("Bibliotheque", plateau[16][16]);
        listSalles.add(salleBibliotheque);

        afficherPorteSalle(salleBibliotheque,16,16);

        Salle salleBillard = new Salle("Salle de billard", plateau[17][9]);
        listSalles.add(salleBillard);

        afficherPorteSalle(salleBillard,17,9);

        Salle salleVeranda = new Salle("Veranda", plateau[18][5]);
        listSalles.add(salleVeranda);

        afficherPorteSalle(salleVeranda,18,5);

    }

    private void afficherPorteSalle(final Salle salle, final int posX, final int posY){

        ImageButton imageSalle = new ImageButton(this);
        File imgFile = new  File("sdcard/map/clickcase.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageSalle.setImageBitmap(myBitmap);
        }
        //imageSalle.setBackgroundResource(R.drawable.clickcase);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(dim, dim);
        imageSalle.setLayoutParams(params);

        imageSalle.setX(x + posX * dim);
        imageSalle.setY(posY * dim);

        fenetreSecondaire.addView(imageSalle);

        imageSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (salle.getCase().getClickable()) {
                    j1.changerCaseJoueur(salle.getCase(), posX, posY);
                }
            }
        });
    }

    private void afficherSalle(ImageView image, int tailleXImage, int tailleYImage, int posX, int posY){

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(tailleXImage * dim, tailleYImage * dim);
        image.setLayoutParams(params);

        image.setX(x + posX * dim);
        image.setY(posY * dim);

        fenetreSecondaire.addView(image);
    }

    public void creerCaseJoueur(int pDim, float pX, float pY, int i, int j, final Joueur pJ){
        imCase = new ImageButton(this);
        pCase = new CaseJoueur(imCase,pJ);

        plateau[i][j] = pCase;

        pJ.setCaseJ(pCase);

        File imgFile = new  File("sdcard/map/pawn.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imCase.setImageBitmap(myBitmap);
        }
        //imCase.setBackgroundResource(R.drawable.pawn);

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

    public void placerImage(final ImageButton imCase, int pDim, float pX, float pY, final Case pCase){

        File imgFile = new  File("sdcard/map/fond.png");
        if(imgFile.exists()){
            myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imCase.setImageBitmap(myBitmap);
        }
        //imCase.setBackgroundResource(R.drawable.fond);


        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(pDim, pDim);
        imCase.setLayoutParams(params);

        imCase.setX(pX);
        imCase.setY(pY);

        fenetreSecondaire.addView(imCase);
        listImCase.add(imCase);

        imCase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pCase.getClickable()) {
                    File imgFile = new  File("sdcard/map/pawn.png");
                    if(imgFile.exists()){
                        myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        imCase.setImageBitmap(myBitmap);
                    }
                    //imCase.setBackgroundResource(R.drawable.pawn);
                    j1.changerCaseJoueur(pCase, 0, 0);
                }
            }
        });

    }
}
