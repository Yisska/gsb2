package fr.be2.gsb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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


    private static final String TAG = "CountriesDbAdapter";

    /**
     * Crée une table par une requete SQL
     */
    private static final String CREATE_TABLE = "CREATE TABLE " + DB_TABLE + " (" + ID_FRAIS +
            " INTEGER PRIMARY KEY AUTOINCREMENT," + TYPE_FRAIS + " TEXT," + QUANTITE + " INTEGER," + DATE_FRAIS
            + " TEXT," + MONTANT + " REAL," + LIBELLE + " TEXT," + DATESAISIE + " DATETIME DEFAULT CURRENT_TIMESTAMP)";



    private static final String CREATE_PARAMETRES="CREATE TABLE PARAMETRES(id int primary key,codev text ,nom text ," +
            "prenom text, email text , urlserveur text ,password text)";

    private static final String INIT_PARAMETRES="INSERT INTO PARAMETRES( ID, CODEV,NOM, PRENOM,EMAIL, URLSERVEUR) Values(1,0,'','','@','https://')";

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
        sqLiteDatabase.execSQL(CREATE_PARAMETRES);
        sqLiteDatabase.execSQL(INIT_PARAMETRES);


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

    public void init_parametres() {
        //on cree une variable de type sqLitedatabase pr pouvoir y acceder
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", 1);
        contentValues.put("CODEV", 0);
        contentValues.put("NOM", "");
        contentValues.put("PRENOM", "");
        contentValues.put("EMAIL", "");
        contentValues.put("URLSERVEUR", "");
        long result = db.insert("PARAMETRES", null, contentValues);
        //  return result != -1;
        return;
    }
    public boolean update_parametre(Integer CodeV, String Nom, String Prenom, String Email, String URL, String MyPassword ) {
        //on cree une variable de type sqLitedatabase pr pouvoir y acceder
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("CODEV",CodeV);
        contentValues.put("NOM", Nom);
        contentValues.put("PRENOM", Prenom);
        contentValues.put("EMAIL", Email);
        contentValues.put("URLSERVEUR", URL);
        if (MyPassword.length()>0)
        {
            contentValues.put("PASSWORD", sha1Hash(MyPassword.toString(),CodeV.toString()));

        }
        long result = db.update("PARAMETRES",contentValues,"ID=1",null);
        return result != -1;

    }


    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "select * from " + DB_TABLE;
        //cursor: type, pointeur: pr parcourir les lignes ds les resultats de la requete. Null car pas de where
        Cursor pointeur = db.rawQuery(query, null);
        return pointeur;

    }

    public Cursor fetchAllFrais() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(DB_TABLE, new String[] { "rowid _id",DATE_FRAIS,
                        MONTANT, DATESAISIE ,LIBELLE},
                null, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
    public Cursor fetchFrais(String filtre) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor mCursor = db.query(DB_TABLE, new String[] { "rowid _id",DATE_FRAIS,
                        MONTANT, DATESAISIE ,LIBELLE},
                filtre, null, null, null, null);

        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor fetch_parametre() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor Cursor = db.rawQuery("Select * from PARAMETRES",null,null);

        return Cursor;
    }


    public SQLHELPER open() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        return this;

    }

    String sha1Hash( String chaine, String cle )
    {
        String hash = null;
        String toHash= chaine + cle;
        try
        {
            MessageDigest digest = MessageDigest.getInstance( "SHA-1" );
            byte[] bytes = toHash.getBytes("UTF-8");
            digest.update(bytes, 0, bytes.length);
            bytes = digest.digest();

            //This is ~55x faster than looping and String.formating()
            hash = bytesToHex( bytes );

        }
        catch( NoSuchAlgorithmException e )
        {
            e.printStackTrace();
        }
        catch( UnsupportedEncodingException e )
        {
            e.printStackTrace();
        }
        return hash;
    }
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex( byte[] bytes )
    {
        char[] hexChars = new char[ bytes.length * 2 ];
        for( int j = 0; j < bytes.length; j++ )
        {
            int v = bytes[ j ] & 0xFF;
            hexChars[ j * 2 ] = hexArray[ v >>> 4 ];
            hexChars[ j * 2 + 1 ] = hexArray[ v & 0x0F ];
        }
        return new String( hexChars );
    }





}

