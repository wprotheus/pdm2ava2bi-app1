package com.wprotheus.wellingtonbpneto.model.repo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class PartidaHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "dbpartida.db";
    protected static final int DB_VERSION = 1;
    protected static final String TABLE_NAME = "tb_partidas";
    protected static final String COLUMN_ID = "ID";
    protected static final String COLUMN_NAME = "NAME";
    protected static final String COLUMN_GUESS = "GUESS";
    protected static final String COLUMN_RESULT = "RESULT";

    protected static final String query =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_GUESS + " TEXT, " +
                    COLUMN_RESULT + " TEXT);";

    public PartidaHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("DB_CREATE", "Creating table: " + TABLE_NAME);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<String> getResultadoPartida() {
        List<String> partidaList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_ID, COLUMN_NAME, COLUMN_GUESS, COLUMN_RESULT},
                null, null, null, null, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String id = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_ID));
                    String nome = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                    String palpite = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_GUESS));
                    String resultado = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESULT));

                    partidaList.add(id);
                    partidaList.add(nome);
                    partidaList.add(palpite);
                    partidaList.add(resultado);
                } while (cursor.moveToNext());
            } else {
                Log.d("DB_QUERY", "No records found in the database.");
            }
            cursor.close();
            db.close();
        }
        return partidaList;
    }
}
