package com.example.maxime.testenvoie;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.maxime.testenvoie.classes.Client;
import com.example.maxime.testenvoie.classes.Couleur;
import com.example.maxime.testenvoie.classes.Server;

import java.util.ArrayList;

public class Menu extends AppCompatActivity {

    //Client public pour l'application
    public static Client client;
    //Pour le serveur
    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;

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
    public EditText saisieNomCreationPartie;
    public ImageButton backToPlayPopupCreationPartie_btn;
    public ImageButton goToSalonCreation_btn;
    public ImageButton redCreationPartie;
    public ImageButton greenCreationPartie;
    public ImageButton blueCreationPartie;
    public ImageButton purpleCreationPartie;
    public ImageButton selectedColorCreationPartie;
    public String couleurChoisieCreationPartie = "";
    public String nomChoisiCreationPartie = "";

    //Popup RejoindrePartie
    public Dialog rejoindrePartiePopup;
    public LinearLayout cadreRejoindrePartie;
    public ImageView texteNomRejoindrePartie;
    public ImageView texteIPRejoindrePartie;
    public EditText saisieNomRejoindrePartie;
    public EditText saisieIPRejoindrePartie;
    public ImageButton backToPlayPopupRejoindrePartie_btn;
    public ImageButton goToSalonRejoindre_btn;
    public String nomChoisiRejoindrePartie = "";
    public String IPChoisieRejoindrePartie = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        play_btn = findViewById(R.id.play_btn);
        editor_btn = findViewById(R.id.editor_btn);
        settings_btn = findViewById(R.id.settings_btn);


        WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);

        int hasWriteContactsPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CODE_ASK_PERMISSIONS);
            }
        } else {
        }
        int hasWriteContactsPermission2 = checkSelfPermission(Manifest.permission.INTERNET);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET},REQUEST_CODE_ASK_PERMISSIONS);
            }
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
                        Server server = new Server();
                        client = new Client("0.0.0.0");
                        //startActivity(new Intent(Menu.this, SalonCreerPartie.class));
                        creationPartiePopup = new Dialog(Menu.this);
                        creationPartiePopup.setContentView(R.layout.popup_creation_partie);
                        cadreCreationPartie = creationPartiePopup.findViewById(R.id.cadreCreationPartie);
                       // cadreCouleursCreationPartie = choixAction.findViewById(R.id.cadreCouleursCreationPartie);
                        backToPlayPopupCreationPartie_btn = creationPartiePopup.findViewById(R.id.backToPlayPopupCreationPartie);
                        texteNomCreationPartie = creationPartiePopup.findViewById(R.id.textNomCreationpartie);
                        texteCouleurCreationPartie = creationPartiePopup.findViewById(R.id.textCouleurCreationpartie);
                        saisieNomCreationPartie = creationPartiePopup.findViewById(R.id.saisieNomCreationPartie);
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
                                nomChoisiCreationPartie = saisieNomCreationPartie.getText().toString();
                                client.setPseudo(nomChoisiCreationPartie);
                                if (nomChoisiCreationPartie.equals("") || couleurChoisieCreationPartie.equals("")) {
                                    Toast.makeText(getBaseContext(), "Veuillez entrer un nom et choisir une couleur", Toast.LENGTH_LONG).show();
                                } else {
                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            client.connect();
                                            switch (couleurChoisieCreationPartie){
                                                case "rouge":
                                                    client.color(0);
                                                    client.setCouleur(0);break;
                                                case "vert":
                                                    client.color(1);
                                                    client.setCouleur(1);break;
                                                case "bleu":
                                                    client.color(2);
                                                    client.setCouleur(2);break;
                                                case "violet":
                                                    client.color(3);
                                                    client.setCouleur(3);break;
                                            }

                                        }
                                    });
                                    thread.start();
                                    thread.interrupt();
                                    startActivity(new Intent(Menu.this, SalonCreerPartie.class));
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




                    }
                });

                rejoindrePartie_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        client = new Client();
                        rejoindrePartiePopup = new Dialog(Menu.this);
                        rejoindrePartiePopup.setContentView(R.layout.popup_rejoindre_partie_1);
                        cadreRejoindrePartie = rejoindrePartiePopup.findViewById(R.id.cadreRejoindrePartie);
                        backToPlayPopupRejoindrePartie_btn = rejoindrePartiePopup.findViewById(R.id.backToPlayPopupRejoindrePartie);
                        texteNomRejoindrePartie = rejoindrePartiePopup.findViewById(R.id.textNomRejoindrePartie);
                        texteIPRejoindrePartie = rejoindrePartiePopup.findViewById(R.id.texteIPRejoindrePartie);
                        saisieNomRejoindrePartie = rejoindrePartiePopup.findViewById(R.id.saisieNomRejoindrePartie);
                        saisieIPRejoindrePartie = rejoindrePartiePopup.findViewById(R.id.saisieIPRejoindrePartie);
                        goToSalonRejoindre_btn = rejoindrePartiePopup.findViewById(R.id.goToSalonRejoindre);


                        backToPlayPopupRejoindrePartie_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                rejoindrePartiePopup.cancel();
                            }
                        });

                        goToSalonRejoindre_btn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                nomChoisiRejoindrePartie = saisieNomRejoindrePartie.getText().toString();
                                IPChoisieRejoindrePartie = saisieIPRejoindrePartie.getText().toString();

                                client.setPseudo(nomChoisiRejoindrePartie);
                                client.setIP(IPChoisieRejoindrePartie);

                                if (nomChoisiRejoindrePartie.equals("") || IPChoisieRejoindrePartie.equals("")) {
                                    Toast.makeText(getBaseContext(), "Veuillez entrer un nom et une adresse IP", Toast.LENGTH_LONG).show();
                                } else {
                                    final Handler handler = new Handler();
                                    Thread thread = new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            handler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Thread thread2 = new Thread(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            client.connect();
                                                        }
                                                    });
                                                    thread2.start();
                                                    thread2.interrupt();
                                                }
                                            },5000);
                                        }
                                    });
                                    thread.start();
                                    thread.interrupt();
                                    startActivity(new Intent(Menu.this, SalonRejoindrePartie.class));
                                }
                            }
                        });

                        rejoindrePartiePopup.getWindow().getDecorView().setSystemUiVisibility(
                                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

                        rejoindrePartiePopup.show();
                        setRejoindrePartiePopupComponentsSize();

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

    public void setComponentsSize() {
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
        rejoindrePartie_btn.getLayoutParams().width = displayWidth / 2;

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
       cadreCreationPartie.getLayoutParams().height = (int)  (displayHeight / 2.3);

       coef = (double) 291/72;
       texteNomCreationPartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
       newHeight = (int)  ((displayHeight / 5) / 3);
       texteNomCreationPartie.getLayoutParams().width = (int) (newHeight * coef);

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

       selectedColorCreationPartie.getLayoutParams().width = displayWidth / 32;
       selectedColorCreationPartie.getLayoutParams().height = displayWidth / 32;
       selectedColorCreationPartie.setX(displayWidth * 2);

    }

    public void setRejoindrePartiePopupComponentsSize() {
        Integer displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        Integer displayHeight = getWindowManager().getDefaultDisplay().getHeight();
        Integer newWidth;
        Integer newHeight;
        Double coef;

        cadreRejoindrePartie.getLayoutParams().width = (int) (displayWidth / 1.5);
        cadreRejoindrePartie.getLayoutParams().height = (int)  (displayHeight / 2.3);

        coef = (double) 291/72;
        texteNomRejoindrePartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
        newHeight = (int)  ((displayHeight / 5) / 3);
        texteNomRejoindrePartie.getLayoutParams().width = (int) (newHeight * coef);

        coef = (double) 416/72;
        texteIPRejoindrePartie.getLayoutParams().height = (int)  ((displayHeight / 5) / 3);
        newHeight = (int)  ((displayHeight / 5) / 3);
        texteIPRejoindrePartie.getLayoutParams().width = (int) (newHeight * coef);

        coef = (double) 628/415;
        backToPlayPopupRejoindrePartie_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        backToPlayPopupRejoindrePartie_btn.getLayoutParams().height = (int)  (newWidth / coef);

        coef = (double) 628/415;
        goToSalonRejoindre_btn.getLayoutParams().width = displayWidth / 6;
        newWidth = displayWidth / 6;
        goToSalonRejoindre_btn.getLayoutParams().height = (int)  (newWidth / coef);


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

    @Override
    public void onStop() {
        super.onStop();
        if (playPopup != null) {
            playPopup.dismiss();
            playPopup = null;
        }
    }
}
