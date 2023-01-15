package fr.be2.gsb2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class fraisauforfait extends MainActivity {

    EditText txtQte;
    Spinner listeForfait;
    String[] Valeurs;
    TextView mSomme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraisauforfait);
        txtQte= findViewById(R.id.quantite);
        listeForfait= findViewById(R.id.spinner_FraisAuForfait);
        Valeurs = getResources().getStringArray(R.array.ValeurForfait);
        mSomme= findViewById(R.id.txtsomme);


        txtQte.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                // This is where we'll check the user input
                Integer q1 = Integer.parseInt(String.valueOf("0"+txtQte.getText()));
                //String f1 = listeForfait1.getSelectedItem().toString();
                int posF1 = listeForfait.getSelectedItemPosition();
                Float s1 = q1 * Float.parseFloat(Valeurs[posF1]);

                mSomme.setText(s1.toString());
            }
        });
    }
}