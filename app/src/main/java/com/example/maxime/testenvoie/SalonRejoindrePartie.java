package com.example.maxime.testenvoie;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.maxime.testenvoie.R;

public class SalonRejoindrePartie extends AppCompatActivity {

    //Pour ne pas que le onFocusChange se refasse plein de fois
    public boolean firstFocus = true;

    //Interface Salon
    public LinearLayout cadreJoueurs;
    public ImageButton backToMenuSalonRejoindrePartie_btn;
    public ImageButton pret_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_rejoindre_partie);
        cadreJoueurs = findViewById(R.id.cadreJoueursRejoindrePartie);
        backToMenuSalonRejoindrePartie_btn = findViewById(R.id.backToMenuSalonRejoindrePartie);
        pret_btn = findViewById(R.id.pret);
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
        backToMenuSalonRejoindrePartie_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SalonRejoindrePartie.this, Menu.class));
            }
        });

        pret_btn.setOnClickListener(new View.OnClickListener() {
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
}
