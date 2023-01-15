package fr.be2.gsb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
            case R.id.main_Button_connexion:
                intent=new Intent(MainActivity.this, connexion.class);

        }startActivity(intent);
        //Toast.makeText(this,MSG, Toast.LENGTH_SHORT).show();

    }
    public void retour(View v){
        this.finish();
    }
}