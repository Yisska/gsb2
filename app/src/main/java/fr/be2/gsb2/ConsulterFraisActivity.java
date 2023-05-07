package fr.be2.gsb2;


import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.FilterQueryProvider;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ConsulterFraisActivity extends MainActivity {

    private SQLHELPER database;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulter_frais);

        database = new SQLHELPER(this);
        database.open();


        //Générer le ListView a partir de SQLite Database
        displayListView();

    }
    private void displayListView() {


        Cursor cursor = database.fetchAllFrais();

        // Les colonnes que l’on veut lier
        String[] columns = new String[] {
                SQLHELPER.LIBELLE,
                SQLHELPER.TYPE_FRAIS,
                SQLHELPER.QUANTITE,
                SQLHELPER.MONTANT,
                SQLHELPER.DATESAISIE
        };
        // Les éléments defnis dans le XML auxquels les données sont liées
        int[] to = new int[] {
                R.id.libelle,
                R.id.typeFrais,
                R.id.quantite,
                R.id.montant,
                R.id.dateSaisie
        };
        //On créer l'adaptateur à l'aide du curseur pointant sur les données souhaitées  ainsi que les informations de mise en page
        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.detailsdesfrais,
                cursor,
                columns,
                to,
                0);

        ListView listView = (ListView) findViewById(R.id.listView1);
        // Attribuer l’adapter au ListView
        listView.setAdapter(dataAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // On obtient le curseur, positionne sur la ligne correspondante dans le jeu de résultats
                Cursor cursor = (Cursor) listView.getItemAtPosition(position);

                // On obtient la Capital du pays
                String countryCode =
                        cursor.getString(cursor.getColumnIndexOrThrow("libelle"));
                Toast.makeText(getApplicationContext(),
                        countryCode, Toast.LENGTH_SHORT).show();

            }
        });

        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                dataAdapter.getFilter().filter(s.toString());
            }
        });
        dataAdapter.setFilterQueryProvider(new FilterQueryProvider() {
            public Cursor runQuery(CharSequence constraint) {
                return database.fetchFrais(constraint.toString());
            }
        });




    }
}
