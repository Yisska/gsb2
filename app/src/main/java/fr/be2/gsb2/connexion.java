package fr.be2.gsb2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class connexion extends AppCompatActivity {
    EditText codeVisiteur;
    EditText email;
    EditText codeVerif;
    LinearLayout envoyer;
    Integer codeAleatoir;
    private static final String MON_FICHIER = "GSB_PREF_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);
        codeVisiteur = findViewById(R.id.codevisiteur);
        email = findViewById(R.id.email);
        envoyer = findViewById(R.id.nouveaumdp);
        codeVerif= findViewById(R.id.codeaffiche);

    }

    public void envoicode(View v){


        Random r = new Random();
        int min = 1000;
        int max = 9999;
        codeAleatoir = r.nextInt((max - min) + 1) + min;
        Toast.makeText(this,"code="+codeAleatoir.toString(),Toast.LENGTH_SHORT).show();
        envoyer.setVisibility(View.VISIBLE);

    }
    public void boncode (View v){
        String codeAleatoirStr = codeAleatoir.toString();
        String codeverifStr = codeVerif.getText().toString(); //récupère le texte et on le transforme en chaine de caractère
        if (codeAleatoirStr.equals(codeverifStr)){
            Toast.makeText(this, "votre code est bon", Toast.LENGTH_SHORT).show(); //Toast=print=afficher
            // ce paragraphe permet de creer le fichier qui stocke les informations de connexion
            getSharedPreferences(MON_FICHIER, MODE_PRIVATE)
                    .edit()
                    .putString("CodeVisiteur", codeVisiteur.getText().toString()) // "CodeVisiteur = nom de la variable et codeVisiteur = valeur de la variable
                    .putString("email", email.getText().toString())
                    .apply();
            Intent intent = new Intent(connexion.this,MainActivity.class); //intent= permet de passer d'une page a l'autre
            startActivity(intent); //lance l'activite intent
        }else{
            Toast.makeText(this, "erreur erreur", Toast.LENGTH_SHORT).show();
        }
    }

}