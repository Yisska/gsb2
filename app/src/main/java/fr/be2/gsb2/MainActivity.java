package fr.be2.gsb2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    Context context;
    private static final String MON_FICHIER = "GSB_PREF_USER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secure();
        setContentView(R.layout.activity_main);
    }
    //fonction en minuscule et le deuxieme mot en majuscule

    public void click_Menu(View vue ){
        //String MSG = "";
        switch(vue.getId()){
            case R.id.main_Button_Fraisauforfait:
                intent=new Intent(MainActivity.this, fraisauforfait.class);
                break;
            case R.id.main_Button_Fraishorsforfait:
                intent=new Intent(MainActivity.this, fraishorsforfait.class);
                break;
            case R.id.main_Button_Synthesedumois:
                intent=new Intent(MainActivity.this, historiquedesenvois.class);
                break;
            case R.id.main_Button_Parametre:
                intent=new Intent(MainActivity.this, parametre.class);
                break;
            case R.id.main_Button_deconnexion:
                intent=new Intent(MainActivity.this, connexion.class);

                SharedPreferences settings = context.getSharedPreferences("PreferencesName", Context.MODE_PRIVATE);
                settings.edit().clear().commit();

        }startActivity(intent);
        //Toast.makeText(this,MSG, Toast.LENGTH_SHORT).show();

    }
    public void retour(View v){
        this.finish();
    }
    public void secure(){  //si je suis connecte amene direct au menu sinon page connexion
        String cvisiteur= getSharedPreferences("GSB_PREF_USER", MODE_PRIVATE).getString("CodeVisiteur","pas authentifie");
        if (cvisiteur.equals("pas authentifie")) {
            Intent intent = new Intent(MainActivity.this,connexion.class);
            startActivity(intent);
        }}

    public void afficherMessage(String titre, String message){
        AlertDialog.Builder builder=new AlertDialog.Builder(this); //classe qui construit une boite de dialogue
        builder.setCancelable(true); //pr que la boite de dialogue soit refermable
        builder.setTitle(titre);
        builder.setMessage(message);
        builder.show();

    }

        }




