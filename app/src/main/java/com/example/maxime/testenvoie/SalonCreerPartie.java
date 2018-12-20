package com.example.maxime.testenvoie;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SalonCreerPartie extends AppCompatActivity {

    //Pour ne pas que le onFocusChange se refasse plein de fois
    public boolean firstFocus = true;

    //Interface Salon
    public LinearLayout cadreJoueurs;
    public ImageButton backToMenuSalonCreerPartie_btn;
    public ImageButton commencerPartie_btn;
    public TextView nomJoueur1;
    public static TextView nomJoueur2;
    public static TextView nomJoueur3;
    public static TextView nomJoueur4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_creer_partie);

        cadreJoueurs = findViewById(R.id.cadreJoueursCreerPartie);
        backToMenuSalonCreerPartie_btn = findViewById(R.id.backToMenuSalonCreerPartie);
        commencerPartie_btn = findViewById(R.id.commencerPartie);
        nomJoueur1 = findViewById(R.id.nomJoueur1);
        nomJoueur2 = findViewById(R.id.nomJoueur2);
        nomJoueur3 = findViewById(R.id.nomJoueur3);
        nomJoueur4 = findViewById(R.id.nomJoueur4);

        nomJoueur1.setText("" + Menu.client.getPseudo());
        switch(Menu.client.couleur){
            case 0:
                nomJoueur1.setTextColor(Color.RED); break;
            case 1:
                nomJoueur1.setTextColor(Color.GREEN); break;
            case 2:
                nomJoueur1.setTextColor(Color.BLUE); break;
            case 3:
                nomJoueur1.setTextColor(Color.BLACK); break;
        }
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
        backToMenuSalonCreerPartie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonCreerPartie.this, Menu.class));
            }
        });

        commencerPartie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // FREDEU
            }
        });
    }

    private void setComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        Integer newWidth;
        Integer newHeight;
        Double coef;


        coef = (double) 628/415;
        backToMenuSalonCreerPartie_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        backToMenuSalonCreerPartie_btn.getLayoutParams().height = (int)  (newWidth / coef);
        backToMenuSalonCreerPartie_btn.setX(percentWidth(10, backToMenuSalonCreerPartie_btn));
        backToMenuSalonCreerPartie_btn.setY(percentHeight(15, backToMenuSalonCreerPartie_btn));

        coef = (double) 950/180;
        commencerPartie_btn.getLayoutParams().width = displayWidth / 2;
        newWidth = displayWidth / 2;
        commencerPartie_btn.getLayoutParams().height = (int)  (newWidth / coef);
        commencerPartie_btn.setX(percentWidth(40, commencerPartie_btn));
        commencerPartie_btn.setY(percentHeight(85, commencerPartie_btn));

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

        switch(player){
            case 1:
                nomJoueur2.setTextColor(c);
                nomJoueur2.setText("" + pseudo);break;
            case 2:
                nomJoueur3.setTextColor(c);
                nomJoueur3.setText("" + pseudo);break;
            case 3:
                nomJoueur4.setTextColor(c);
                nomJoueur4.setText("" + pseudo);break;
        }
    }
}
