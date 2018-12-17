package com.example.maxime.testenvoie;

import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ThemeSallesActivity extends AppCompatActivity {

    public RelativeLayout obj_cadreApercuPlateau_RL;
    public ImageView obj_salleLogo_IV;

    public List<ImageButton> lst_salles_IB;

    public LinearLayout obj_fenetrePrincipale_LL;
    public ImageButton obj_imageClick_IB;

    public ImageButton obj_gallerie_IB;
    public ImageButton obj_changerNom_IB;
    public TextView obj_nomSalle_TV;

    public Float decalageX;
    public Float decalageY;

    public List<String> lst_nomsSalles ;

    public int i;

    public boolean var_placement = false;


    public Dialog obj_Popup_Dlg;

    public ImageButton obj_goToPersonnages_Btn;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_theme_salles);


        //Récupération des objets définits dans le XML
        obj_fenetrePrincipale_LL = findViewById(R.id.fenetrePrincipale_LL);
        obj_cadreApercuPlateau_RL = findViewById(R.id.cadreApercuPlateau_RL);

        lst_salles_IB = new ArrayList<ImageButton>();
        obj_gallerie_IB = findViewById(R.id.gallerie_IB);
        obj_changerNom_IB = findViewById(R.id.changerNom_IB);
        obj_nomSalle_TV = findViewById(R.id.nomSalle_TV);
        obj_goToPersonnages_Btn = findViewById(R.id.goToPersonnages_Btn);

//        obj_gallerie_IB.setClickable(false);
//        obj_changerNom_IB.setClickable(false);
        obj_gallerie_IB.setEnabled(false);
        obj_changerNom_IB.setEnabled(false);

        lst_nomsSalles = new ArrayList<String>();
//        Integer test = obj_cadreApercuPlateau_RL.getChildCount();
//        Log.i("YAAAY: ", test.toString());

        obj_goToPersonnages_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThemeSallesActivity.this, ThemePersonnagesActivity.class));
            }
        });



        for (i = 0 ; i < obj_cadreApercuPlateau_RL.getChildCount() ; i++) {
            lst_salles_IB.add((ImageButton) obj_cadreApercuPlateau_RL.getChildAt(i));
            //Log.i("SALLE : ", obj_cadreApercuPlateau_RL.getChildAt(i).toString());

            for (int i = 0 ; i < obj_cadreApercuPlateau_RL.getChildCount() ; i++) {

                           lst_nomsSalles.add("Salle " + (i + 1));
//                        Log.i("YAAAY: ", tab_nomsSalles);

            }


            lst_salles_IB.get(i).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v1) {
                    obj_gallerie_IB.setEnabled(true);
                    obj_changerNom_IB.setEnabled(true);
                    String str = "Salle " + (i + 1);


                    //Afficher le nom de la salle correspondante (à changer)
                    for (int y = 0 ; y < lst_salles_IB.size() ; y++) {
                        if (lst_salles_IB.get(y).equals(v1)) {
                            obj_nomSalle_TV.setText(lst_nomsSalles.get(y));
                        }
//                        Log.i("YAAAY: ", tab_nomsSalles);

                    }

//                    for(int yy = 0; yy<lst_nomsSalles.size(); yy++) {
//                        Log.i("OUAIS ", lst_nomsSalles.get(yy));
//                    }

                    obj_changerNom_IB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v3) {
                            Log.e("TEST : ", "OUIIIII" );
                            obj_Popup_Dlg = new Dialog(ThemeSallesActivity.this);
                            obj_Popup_Dlg.setContentView(R.layout.popup_theme);
                            final EditText obj_nouveauNomPopUP_ET;
                            Button obj_enregistrerPopUp_Btn;
                            obj_nouveauNomPopUP_ET = obj_Popup_Dlg.findViewById(R.id.nouveauNom_ET);
                            obj_enregistrerPopUp_Btn = obj_Popup_Dlg.findViewById(R.id.enregistrer_Btn);

                            obj_nouveauNomPopUP_ET.setEnabled(true);
                            obj_enregistrerPopUp_Btn.setEnabled(true);
                            Integer size = obj_enregistrerPopUp_Btn.getWidth();
                            Log.e("size = ", size.toString());
                            obj_Popup_Dlg.show();
                            obj_enregistrerPopUp_Btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v4) {

                                    for (int y = 0 ; y < lst_salles_IB.size() ; y++) {
                                        if (lst_salles_IB.get(y).equals(v1)) {
                                            lst_nomsSalles.add(y, obj_nouveauNomPopUP_ET.getText().toString());
                                            obj_nomSalle_TV.setText(obj_nouveauNomPopUP_ET.getText().toString());
                                            obj_Popup_Dlg.cancel();
                                        }

                                    }

                                }
                            });
                        }
                    });
                    obj_gallerie_IB.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v2) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 0);
                            obj_imageClick_IB = (ImageButton) v1;
                        }
                    });




                }
            });
        }






        //setElements();


    }




    private void setCadre() {

        //taille du cadre d'aperçu du plateau
        Integer height =  obj_cadreApercuPlateau_RL.getBottom() - obj_cadreApercuPlateau_RL.getTop();
        obj_cadreApercuPlateau_RL.getLayoutParams().width = (int) (height + decalageY);
        Integer width = obj_cadreApercuPlateau_RL.getLayoutParams().width;

        Log.i("WIDTH : ", width.toString());

        //taille des sallesz
        int tailleSalle = height / 4;

        for (ImageButton ib : lst_salles_IB) {
            ib.getLayoutParams().width = tailleSalle;
            ib.getLayoutParams().height = tailleSalle;
        }




        //Disposition des salles
            //on place les salles dans les coins en premier (salles 1,3,6,8)
            lst_salles_IB.get(0).setX(0);
            lst_salles_IB.get(0).setY(0);
            lst_salles_IB.get(2).setX(width - tailleSalle);
            lst_salles_IB.get(2).setY(0);
            lst_salles_IB.get(5).setX(width - tailleSalle);
            lst_salles_IB.get(5).setY(height + decalageY - tailleSalle);
            lst_salles_IB.get(7).setX(0);
            lst_salles_IB.get(7).setY(height + decalageY - tailleSalle);

            //on place les salles au milieu des côtés haut, bas et gauche (salles 2,7,9)
            lst_salles_IB.get(1).setX(width / 2 - (tailleSalle/2));
            lst_salles_IB.get(1).setY(0);
            lst_salles_IB.get(6).setX((width / 2) - (tailleSalle/2));
            lst_salles_IB.get(6).setY(height + decalageY - tailleSalle);
            lst_salles_IB.get(8).setX(0);
            lst_salles_IB.get(8).setY(((height  + decalageY) / 2 ) - tailleSalle / 2);

            //on place les deux salles du milieu du côté droit (salles 4,5)
            lst_salles_IB.get(3).setX(width - tailleSalle);
            lst_salles_IB.get(3).setY((float) ((height  + decalageY) * 0.25) );
            lst_salles_IB.get(4).setX(width - tailleSalle);
            lst_salles_IB.get(4).setY((float) ((height  + decalageY) * 0.75) - tailleSalle );




    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0){

            if (resultCode == RESULT_OK){


                Uri uri = data.getData();

                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    // Log.d(TAG, String.valueOf(bitmap));
                    loadImageFromStorage(saveToInternalStorage(bitmap));

                   /* obj_salle1_IB.setImageBitmap(bitmap);
                    ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(200,200);
                    obj_salle1_IB.setLayoutParams(params);*/
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (resultCode == RESULT_CANCELED){
                onBackPressed();
            }
        }
    }


    //Pour mettre l'image dans l'imageButton
    private void loadImageFromStorage(String path)
    {

        try {
            File f=new File(path, "profile.jpg");
            Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));

            obj_imageClick_IB.setImageBitmap(b);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }

    }

    // Pour enregistrer l'image dans un dossier de l'appli
    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory,"profile.jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Log.i("PATH : ", directory.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return directory.getAbsolutePath();
    }




    @Override
    public void onWindowFocusChanged(boolean hasFocus) {

        super.onWindowFocusChanged(hasFocus);


        int[] coordoneesAbsolutsFenetrePrincipale = {0,0};
        obj_fenetrePrincipale_LL.getLocationOnScreen(coordoneesAbsolutsFenetrePrincipale);
        decalageX = Math.abs(obj_fenetrePrincipale_LL.getX() - coordoneesAbsolutsFenetrePrincipale[0]);
        decalageY = Math.abs(obj_fenetrePrincipale_LL.getY() - coordoneesAbsolutsFenetrePrincipale[1]);



        Log.i("Decalage X : ", decalageX.toString());
        Log.i("Decalage Y : ", decalageY.toString());

        if(!var_placement) {
            setCadre();
            var_placement = true;
        }


            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

//        setComponentsSize();
        }

    private void setComponentsSize() {
        int displayWidth = getWindowManager().getDefaultDisplay().getWidth();
        int width;
        int height;
        Integer newWidth;
        Double coef;


        coef = (double) obj_goToPersonnages_Btn.getWidth() / obj_goToPersonnages_Btn.getHeight();
        obj_goToPersonnages_Btn.getLayoutParams().width = (int) (displayWidth / 3);
        newWidth = obj_goToPersonnages_Btn.getLayoutParams().width;
        obj_goToPersonnages_Btn.getLayoutParams().height = (int)  (newWidth / coef);




    }


}
