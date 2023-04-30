package fr.be2.gsb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLHELPER extends SQLiteOpenHelper {
    //declaration des variables
    public static final String DB_NAME = "GSB.db";
    public static final String DB_TABLE = "FRAIS";
    public static final String ID_FRAIS = "ID_FRAIS"; //ce sera les colonnes de la table frais
    public static final String TYPE_FRAIS = "TYPE_FRAIS";
    public static final String QUANTITE = "QUANTITE";
    public static final String DATE_FRAIS = "DATE_FRAIS";
    public static final String MONTANT = "MONTANT";
    public static final String DATESAISIE = "DATESAISIE";
    public static final String LIBELLE = "LIBELLE";

    /**
     * Crée une table par une requete SQL
     */
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" + ID_FRAIS +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_FRAIS + " TEXT," + QUANTITE + " INTEGER," + DATE_FRAIS
            + " TEXT," + MONTANT + " REAL," + LIBELLE + " TEXT," + DATESAISIE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";


    /**
     *
     * @param context
     */
    public SQLHELPER(Context context) {

        super(context, DB_NAME, null, 1);//permet d'acceder aux membres de la classe mère

    }


    /**
     * constructeur de la classe
     * methode venant de SQLLite et permettant d'executer une requete SQL
     * @param sqLiteDatabase
     */
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE);


    }



    /**
     * destructeur de la classe
     * @param sqLiteDatabase
     * @param i
     * @param i1
     */
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //onUpgrade = met à jour la table
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + DB_TABLE);
        onCreate(sqLiteDatabase);
    }

    /**
     * Insère dans le BDD les données type, quantité, date, montant et libellé renseignées par le visiteur
     * @param typeForfait
     * @param quantite
     * @param dateForfait
     * @param montant
     * @param libelle
     * @return booleen
     */

    //insertdata = fct permettant d'inserer des infos
    public boolean insertData(String typeForfait, Integer quantite, String dateForfait, double montant, String libelle) {
        //on cree une variable de type sqLitedatabase pr pouvoir y acceder
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TYPE_FRAIS, typeForfait);
        contentValues.put(QUANTITE, quantite);
        contentValues.put(DATE_FRAIS, dateForfait);
        contentValues.put(MONTANT, montant);
        contentValues.put(LIBELLE, libelle);
        //insert sert a inserer des donnees, elle insere ds notre table contentValue les contenus
        // des variables que l'utilisateur renseigne
        long result = db.insert(DB_TABLE, null, contentValues);
        return result != -1;

    }


}

