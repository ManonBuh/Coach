package com.example.coach.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import com.example.coach.model.Profil;
import android.database.Cursor;
import java.util.Date;

public class ProfilDAO extends SQLiteOpenHelper {

    // Nom de la base
    private static final String DATABASE_NAME = "coach.db";

    // Version de la base
    private static final int DATABASE_VERSION = 1;

    // Nom de la table
    private static final String TABLE_PROFIL = "profil";

    // Colonnes
    private static final String COL_POIDS = "poids";
    private static final String COL_TAILLE = "taille";
    private static final String COL_AGE = "age";
    private static final String COL_SEXE = "sexe";
    private static final String COL_DATE = "dateMesure";

    // Constructeur
    public ProfilDAO(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + TABLE_PROFIL + " (" +
                COL_DATE + " INTEGER PRIMARY KEY, " +
                COL_POIDS + " INTEGER, " +
                COL_TAILLE + " INTEGER, " +
                COL_AGE + " INTEGER, " +
                COL_SEXE + " INTEGER)";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROFIL);
        onCreate(db);
    }

    // Enregistre un profil dans la base
    public void insertProfil(Profil profil) {

        // Ouvre la base en écriture
        SQLiteDatabase db = this.getWritableDatabase();

        // Dictionnaire des valeurs à insérer
        ContentValues values = new ContentValues();

        // Ajout des champs
        values.put(COL_POIDS, profil.getPoids());
        values.put(COL_TAILLE, profil.getTaille());
        values.put(COL_AGE, profil.getAge());
        values.put(COL_SEXE, profil.getSexe());

        // La date est stockée en long (timestamp)
        values.put(COL_DATE, profil.getDateMesure().getTime());

        // Insertion
        db.insert(TABLE_PROFIL, null, values);

        // Fermeture de la base
        db.close();
    }

    // Retourne le dernier profil enregistré dans la base
    public Profil getLastProfil() {

        // Ouvre la base en lecture
        SQLiteDatabase db = this.getReadableDatabase();

        // Par défaut, aucun profil
        Profil profil = null;

        // Requête pour récupérer le dernier profil enregistré
        String query = "SELECT * FROM " + TABLE_PROFIL +
                " ORDER BY " + COL_DATE + " DESC LIMIT 1";

        // Exécution de la requête
        Cursor cursor = db.rawQuery(query, null);

        // Si une ligne existe
        if (cursor.moveToFirst()) {

            // Récupération des valeurs
            int poids = cursor.getInt(cursor.getColumnIndexOrThrow(COL_POIDS));
            int taille = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TAILLE));
            int age = cursor.getInt(cursor.getColumnIndexOrThrow(COL_AGE));
            int sexe = cursor.getInt(cursor.getColumnIndexOrThrow(COL_SEXE));
            long dateMesure = cursor.getLong(cursor.getColumnIndexOrThrow(COL_DATE));

            // Création de l'objet Profil
            profil = new Profil(poids, taille, age, sexe, new Date(dateMesure));
        }

        // Fermeture
        cursor.close();
        db.close();

        return profil;
    }
}