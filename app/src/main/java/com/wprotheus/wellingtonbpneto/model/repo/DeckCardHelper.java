package com.wprotheus.wellingtonbpneto.model.repo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class DeckCardHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "dbmatch.db";
    protected static final int DB_VERSION = 1;
    protected static final String TABLE_NAME = "tb_deck_cards";
    protected static final String COLUMN_CODE = "CODIGO";
    protected static final String COLUMN_IMAGE = "IMAGEM";
    protected static final String COLUMN_VALUE = "VALOR";
    protected static final String COLUMN_SUIT = "NAIPE";

    protected static final String query =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_CODE + " TEXT, " +
                    COLUMN_IMAGE + " TEXT, " +
                    COLUMN_VALUE + " TEXT, " +
                    COLUMN_SUIT + " TEXT);";

    public DeckCardHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        Log.d("DB_CREATE", "Creating table: " + TABLE_NAME);
        db.execSQL(query);
    }

    public void clearDatabase() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
        db.close();
        Log.d("DB_CLEANUP", "Database cleared.");
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public List<String> getBaralhoBaixado() {
        List<String> deckCards = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(
                TABLE_NAME,
                new String[]{COLUMN_CODE, COLUMN_IMAGE, COLUMN_VALUE, COLUMN_SUIT},
                null, null, null, null, null);

        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    String code = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CODE));
                    String image = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE));
                    String value = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_VALUE));
                    String suit = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUIT));

                    String card = value + " de " + suit + " (" + code + ")";
                    deckCards.add(code);
                    deckCards.add(image);
                    deckCards.add(value);
                    deckCards.add(suit);
                } while (cursor.moveToNext());
            } else {
                Log.d("DB_QUERY", "No records found in the database.");
            }
            cursor.close();
            db.close();
        }
        return deckCards;
    }
}