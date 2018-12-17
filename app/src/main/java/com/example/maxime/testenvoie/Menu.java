package com.example.maxime.testenvoie;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    //Pour ne pas que le onFocusChange se refasse plein de fois
    public boolean firstFocus = true;


    //Interface Menu
    public ImageButton play_btn;
    public ImageButton editor_btn;
    public ImageButton settings_btn;

    //Popup play
    public Dialog playPopup;
    public LinearLayout playPopupWindow;
    public ImageButton creerPartie_btn;
    public ImageButton rejoindrePartie_btn;
    public ImageButton backToMenu_btn;

    //Popup CréationPartie
    public Dialog creationPartiePopup;
    public LinearLayout cadreCreationPartie;
    public ImageView texteNomCreationPartie;
    public ImageView texteCouleurCreationPartie;
    public EditText saisieNomCreationpartie;
    public ImageButton backToPlayPopup_btn;
    public ImageButton goToSalonCreation_btn;
    public ImageButton redCreationPartie;
    public ImageButton greenCreationPartie;
    public ImageButton blueCreationPartie;
    public ImageButton purpleCreationPartie;
    public ImageButton selectedColorCreationPartie;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        play_btn = findViewById(R.id.play_btn);
        editor_btn = findViewById(R.id.editor_btn);
        settings_btn = findViewById(R.id.settings_btn);



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
        play_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playPopup = new Dialog(Menu.this);
                playPopup.setContentView(R.layout.popup_play);
                creerPartie_btn = playPopup.findViewById(R.id.creerPartie_btn);
                rejoindrePartie_btn = playPopup.findViewById(R.id.rejoindrePartie_btn);
                backToMenu_btn = playPopup.findViewById(R.id.retour_btn);
                playPopup.getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);



                playPopup.show();
                setPlayPopupComponentsSize();

                creerPartie_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //startActivity(new Intent(Menu.this, SalonCreerPartie.class));
                        creationPartiePopup = new Dialog(Menu.this);
                        creationPartiePopup.setContentView(R.layout.popup_creation_partie);
                        cadreCreationPartie = creationPartiePopup.findViewById(R.id.cadreCreationPartie);
                       // cadreCouleursCreationPartie = creationPartiePopup.findViewById(R.id.cadreCouleursCreationPartie);
                        backToPlayPopup_btn = creationPartiePopup.findViewById(R.id.backToPlayPopup);
                        texteNomCreationPartie = creationPartiePopup.findViewById(R.id.textNomCreationpartie);
                        texteCouleurCreationPartie = creationPartiePopup.findViewById(R.id.textCouleurCreationpartie);
                        saisieNomCreationpartie = creationPartiePopup.findViewById(R.id.saisieNomCreationPartie);
                        goToSalonCreation_btn = creationPartiePopup.findViewById(R.id.goToSalonCreation);
                        redCreationPartie = creationPartiePopup.findViewById(R.id.redCreationPartie);
                        greenCreationPartie = creationPartiePopup.findViewById(R.id.greenCreationPartie);
                        blueCreationPartie = creationPartiePopup.findViewById(R.id.blueCreationPartie);
                        purpleCreationPartie = creationPartiePopup.findViewById(R.id.purpleCreationPartie);
                        selectedColorCreationPartie = creationPartiePopup.findViewById(R.id.selectedColorCreationPartie);


                        final ArrayList<ImageButton> couleurs= new ArrayList<ImageButton>();
                        couleurs.add(redCreationPartie);
                        couleurs.add(greenCreationPartie);
                        couleurs.add(blueCreationPartie);
                        couleurs.add(purpleCreationPartie);

                        creationPartiePopup.getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                        creationPartiePopup.show();
                        setCreationPartiePopupComponentsSize();

                        backToPlayPopup_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                creationPartiePopup.cancel();
                            }
                        });

                        for (final ImageButton ib : couleurs) {
                            ib.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                            selectedColorCreationPartie.setX(ib.getX() + (ib.getLayoutParams().width / 4));
                                            selectedColorCreationPartie.setY(ib.getY() + (ib.getLayoutParams().height / 4));



                                }
                            });
                        }
                    }
                });

                backToMenu_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        playPopup.cancel();
                    }
                });


            }
        });

        editor_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Menu.this, ThemeSallesActivity.class));
            }
        });

        settings_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void setComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer newWidth;
        Double coef;

        coef = (double) play_btn.getWidth() / play_btn.getHeight();
        play_btn.getLayoutParams().width = (int) (displayWidth / 2.5);
        newWidth = play_btn.getLayoutParams().width;
        play_btn.getLayoutParams().height = (int)  (newWidth / coef);
        play_btn.setX(percentWidth(20, play_btn));
        play_btn.setY(percentHeight(65, play_btn));


        coef = (double) editor_btn.getWidth() / editor_btn.getHeight();
        editor_btn.getLayoutParams().width = (int) (displayWidth / 2.5);
        newWidth = editor_btn.getLayoutParams().width;
        editor_btn.getLayoutParams().height = (int)  (newWidth / coef);
        editor_btn.setX(percentWidth(80, editor_btn));
        editor_btn.setY(percentHeight(65, editor_btn));


        coef = (double) settings_btn.getWidth() / settings_btn.getHeight();
        settings_btn.getLayoutParams().width = (int) (displayWidth / 6);
        newWidth = settings_btn.getLayoutParams().width;
        settings_btn.getLayoutParams().height = (int)  (newWidth / coef);
        settings_btn.setX(percentWidth(50, settings_btn));
        settings_btn.setY(percentHeight(80, settings_btn));







    }

    public void setPlayPopupComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer newWidth;
        Double coef;

        coef = (double) 887/180;
        creerPartie_btn.getLayoutParams().width = displayWidth / 2;
        newWidth = displayWidth / 2;
        creerPartie_btn.getLayoutParams().height = (int)  (newWidth / coef);

        rejoindrePartie_btn.getLayoutParams().height = (int)  (newWidth / coef);
        //retour_btn.getLayoutParams().height = (int) (newWidth / coef);

        rejoindrePartie_btn.getLayoutParams().width = displayWidth / 2;
        //retour_btn.getLayoutParams().width = displayWidth / 3;


        coef = (double) 628/415;
        backToMenu_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        backToMenu_btn.getLayoutParams().height = (int)  (newWidth / coef);

    }

   public void setCreationPartiePopupComponentsSize() {
       Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
       Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
       Integer newWidth;
       Integer newHeight;
       Double coef;


       cadreCreationPartie.getLayoutParams().width = (int) (displayWidth / 1.5);
       cadreCreationPartie.getLayoutParams().height = (int)  (displayHeight / 2);
     /*  cadreNom.setX(percentWidth(50, cadreNom));
       cadreNom.setY(percentHeight(20, cadreNom));*/

       //cadreCouleursCreationPartie.getLayoutParams().width = (int) (displayWidth / 1.5);
       //cadreCouleursCreationPartie.getLayoutParams().height = (int)  (displayHeight / 5);
       /*cadreCouleurs.setX(percentWidth(50, cadreCouleurs));
       cadreCouleurs.setY(percentHeight(80, cadreCouleurs));*/

       coef = (double) 291/72;
       texteNomCreationPartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
       newHeight = (int)  ((displayHeight / 5) / 3);
       texteNomCreationPartie.getLayoutParams().width = (int) (newHeight * coef);

       coef = (double) 462/72;
       texteCouleurCreationPartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
       newHeight = (int)  ((displayHeight / 5) / 3);
       texteCouleurCreationPartie.getLayoutParams().width = (int) (newHeight * coef);

       coef = (double) 628/415;
       backToPlayPopup_btn.getLayoutParams().width = displayWidth / 6;
       newWidth = displayWidth / 6;
       backToPlayPopup_btn.getLayoutParams().height = (int)  (newWidth / coef);

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



       selectedColorCreationPartie.getLayoutParams().width = displayWidth / 32;
       selectedColorCreationPartie.getLayoutParams().height = displayWidth / 32;
       selectedColorCreationPartie.setX(displayWidth * 2);



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
        return (((getWindowManager().getDefaultDisplay().getHeight()) * percent) / 100)  - (v.getLayoutParams().height / 2);

    }
}
