package fr.be2.gsb2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;

public class fraisauforfait extends MainActivity {

    EditText txtQte;
    Spinner listeForfait;
    String[] Valeurs;
    TextView mSomme;
    Float montantCalcule;
    TextView maDate; //maDate correspond a selectionnerdate et date correspond a la nomination dans laquelle je met maDate
    SQLHELPER database;
    DatePickerDialog picker;
    Calendar calendrier = Calendar.getInstance(); //on declare une classe d'un calendrier qui existe deja
    int jj=calendrier.get(Calendar.DAY_OF_MONTH);
    int mm=calendrier.get(Calendar.MONTH);
    int aaaa=calendrier.get(Calendar.YEAR);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fraisauforfait);
        database= new SQLHELPER(this);
        txtQte= findViewById(R.id.quantite);
        listeForfait= findViewById(R.id.spinner_FraisAuForfait);
        Valeurs = getResources().getStringArray(R.array.ValeurForfait);
        mSomme= findViewById(R.id.txtsomme);
        maDate = findViewById(R.id.selectionnerdate);



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
                Integer q1 = Integer.parseInt(String.valueOf("0"+txtQte.getText().toString()));
                //String f1 = listeForfait1.getSelectedItem().toString();
                int posF1 = listeForfait.getSelectedItemPosition();
                Float s1 = q1 * Float.parseFloat(Valeurs[posF1]);

                mSomme.setText(s1.toString());
            }
        });}
    public void ShowCal(View vv)
    {
        picker = new DatePickerDialog(fraisauforfait.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        maDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        //date qu'on recupere une fois qu'on a selectionne
                    }
                },aaaa, mm, jj);//date qui s'affiche sur le calendrier
        picker.show();
    }

        public void One_click(View v){
        Integer quantite =Integer.parseInt(String.valueOf(txtQte.getText().toString()));
        String FraisAuForfait = listeForfait.getSelectedItem().toString();
        montantCalcule = Float.parseFloat(mSomme.getText().toString());
        String date= maDate.getText().toString();
            if (database.insertData(FraisAuForfait, quantite, date, montantCalcule, FraisAuForfait)) {
                afficherMessage("Succès", "Valeur ajoutée. " + "Montant= " + mSomme);
                return;
            }
            }


        }

