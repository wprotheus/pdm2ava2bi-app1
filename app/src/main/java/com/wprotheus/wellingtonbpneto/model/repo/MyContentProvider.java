package com.wprotheus.wellingtonbpneto.model.repo;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyContentProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.wprotheus.wellingtonbpneto";
    private static final String URL_BARALHO = "content://" + PROVIDER_NAME + "/deck_cards";
    public static final Uri CONTENT_URI_BARALHO = Uri.parse(URL_BARALHO);
    private static final String URL_PARTIDA = "content://" + PROVIDER_NAME + "/partidas";
    public static final Uri CONTENT_URI_PARTIDA = Uri.parse(URL_PARTIDA);

    private static final int DECK_CARDS = 1;
    private static final int PARTIDAS = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(PROVIDER_NAME, "deck_cards", DECK_CARDS);
        uriMatcher.addURI(PROVIDER_NAME, "partidas", PARTIDAS);
    }

    private DeckCardHelper deckCardHelper;
    private PartidaHelper partidaHelper;
    private SQLiteDatabase dbBaralho;
    private SQLiteDatabase dbPartida;

    @Override
    public boolean onCreate() {
        deckCardHelper = new DeckCardHelper(getContext());
        partidaHelper = new PartidaHelper(getContext());
        dbBaralho = deckCardHelper.getWritableDatabase();
        dbPartida = partidaHelper.getWritableDatabase();
        return dbBaralho != null && dbPartida != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
                        @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db;
        String table;
        Log.d("MyContentProvider", "query() called with URI: " + uri.toString());
        switch (uriMatcher.match(uri)) {
            case DECK_CARDS:
                db = dbBaralho;
                table = DeckCardHelper.TABLE_NAME;
                break;
            case PARTIDAS:
                db = dbPartida;
                table = PartidaHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }
        Cursor cursor = db.query(table, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)) {
            case DECK_CARDS:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME + ".deck_cards";
            case PARTIDAS:
                return "vnd.android.cursor.dir/vnd." + PROVIDER_NAME + ".partidas";
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        SQLiteDatabase db;
        String table;
        switch (uriMatcher.match(uri)) {
            case DECK_CARDS:
                db = dbBaralho;
                table = DeckCardHelper.TABLE_NAME;
                break;
            case PARTIDAS:
                db = dbPartida;
                table = PartidaHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }

        long rowID = db.insert(table, null, values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            getContext().getContentResolver().notifyChange(_uri, null);
            return _uri;
        }
        throw new SQLException("Erro ao gravar dados: " + uri);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db;
        String table;
        switch (uriMatcher.match(uri)) {
            case DECK_CARDS:
                db = dbBaralho;
                table = DeckCardHelper.TABLE_NAME;
                break;
            case PARTIDAS:
                db = dbPartida;
                table = PartidaHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }
        int count = db.delete(table, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db;
        String table;
        switch (uriMatcher.match(uri)) {
            case DECK_CARDS:
                db = dbBaralho;
                table = DeckCardHelper.TABLE_NAME;
                break;
            case PARTIDAS:
                db = dbPartida;
                table = PartidaHelper.TABLE_NAME;
                break;
            default:
                throw new IllegalArgumentException("URI inválida: " + uri);
        }
        int count = db.update(table, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}