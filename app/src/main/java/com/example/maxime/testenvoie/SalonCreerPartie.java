package com.example.maxime.testenvoie;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;

public class SalonCreerPartie extends AppCompatActivity {

    //Pour ne pas que le onFocusChange se refasse plein de fois
    public boolean firstFocus = true;

    //Interface Menu
    public LinearLayout cadreJoueurs;
    public ImageButton editor_btn;
    public ImageButton settings_btn;
    public LinearLayout bande;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon_creer_partie);

        cadreJoueurs = findViewById(R.id.cadreJoueurs);
        //bande = findViewById(R.id.bande);
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
            //setComponentsListeners();
            firstFocus = false;
        }
    }

    private void setComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        Integer newWidth;
        Integer newHeight;
        Double coef;




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
