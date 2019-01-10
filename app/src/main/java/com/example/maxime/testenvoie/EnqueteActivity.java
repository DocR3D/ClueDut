package com.example.maxime.testenvoie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.maxime.testenvoie.classes.Server;

public class EnqueteActivity extends AppCompatActivity {

    public static CheckBox[] checkBoxs = new CheckBox[21];

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiche_enquete);

        final Button retour = (Button) findViewById(R.id.retour );

        colonnelMoutarde = (CheckBox) findViewById(R.id.ColonelMoutarde);
        reverandOlive = (CheckBox) findViewById(R.id.ReverandOlive);
        professeurViolet = (CheckBox) findViewById(R.id.ProfesseurViolet);
        madameLeBlanc = (CheckBox) findViewById(R.id.MadameLeblanc);
        madamePervenche = (CheckBox) findViewById(R.id.MadamePervenche);
        mademoiselleRose = (CheckBox) findViewById(R.id.MademoiselleRose);

        poignard = (CheckBox) findViewById(R.id.poignard);
        chandelier = (CheckBox) findViewById(R.id.chandelier);
        corde = (CheckBox) findViewById(R.id.corde);
        cleAnglaise = (CheckBox) findViewById(R.id.cleAnglaise);
        revolver = (CheckBox) findViewById(R.id.Revolver);
        matraque = (CheckBox) findViewById(R.id.matraque);

        salleAManger = (CheckBox) findViewById(R.id.salleAManger);
        salleDeBal = (CheckBox) findViewById(R.id.salleDeBal);
        salleDeBillard = (CheckBox) findViewById(R.id.billard);
        salon = (CheckBox) findViewById(R.id.salon);
        hall = (CheckBox) findViewById(R.id.hall);
        bureau = (CheckBox) findViewById(R.id.bureau);
        bibliotheque = (CheckBox) findViewById(R.id.bibliotheque);
        veranda = (CheckBox) findViewById(R.id.verenda);
        cuisine = (CheckBox) findViewById(R.id.Cuisine);

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

        checkBoxs[12] = salleDeBillard;
        checkBoxs[13] = salleAManger;
        checkBoxs[14] = salleDeBal;
        checkBoxs[15] = salon;
        checkBoxs[16] = bureau;
        checkBoxs[17] = bibliotheque;
        checkBoxs[18] = hall;
        checkBoxs[19] = cuisine;
        checkBoxs[20] = veranda;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Menu.client.initjeu();
            }
        });
        thread.start();
        thread.interrupt();

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retour();
            }
        });
    }

    private void retour() {
        Intent intent = new Intent(this, JouerActivity.class);
        startActivity(intent);
    }
}
