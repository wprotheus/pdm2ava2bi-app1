package com.wprotheus.wellingtonbpneto.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MySharedDataPref {
    private static final String MYPREF = "Salvando_Baralho";
    private static final String NOME_KEY = "nome";
    private static final String QUANTIDADE_KEY = "quantidade";
    private static final String RANDOM_LIST_CODE_KEY = "codigoCarta";
    private static final String RANDOM_LIST_IMAGE_KEY = "imagemCarta";
    private static final String RANDOM_LIST_VALUE_KEY = "valorCarta";
    private static final String RANDOM_LIST_SUIT_KEY = "naipeCarta";
    private static final String RESULT_KEY = "resultado";

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Gson gson;

    public MySharedDataPref(Context context) {
        this.preferences = context.getApplicationContext()
                .getSharedPreferences(MYPREF, Context.MODE_PRIVATE);
        this.editor = preferences.edit();
        gson = new Gson();
    }

    public void salvarNome(String nome) {
        editor.putString(NOME_KEY, nome);
        editor.apply();
    }

    public String recuperarNome() {
        return preferences.getString(NOME_KEY, null);
    }

    public void removerNome() {
        editor.remove(NOME_KEY);
        editor.apply();
    }

    public void salvarQtdCartas(String qtdCartas) {
        editor.putString(QUANTIDADE_KEY, qtdCartas);
        editor.apply();
    }

    public String recuperarQtdCartas() {
        return preferences.getString(QUANTIDADE_KEY, null);
    }

    public void removerQtdCartas() {
        editor.remove(QUANTIDADE_KEY);
        editor.apply();
    }

    public void salvarCartasEscolhidas(List<String> code, List<String> image, List<String> value, List<String> suit) {
        String codigo = gson.toJson(code);
        String imagem = gson.toJson(image);
        String valor = gson.toJson(value);
        String naipe = gson.toJson(suit);
        editor.putString(RANDOM_LIST_CODE_KEY, codigo);
        editor.putString(RANDOM_LIST_IMAGE_KEY, imagem);
        editor.putString(RANDOM_LIST_VALUE_KEY, valor);
        editor.putString(RANDOM_LIST_SUIT_KEY, naipe);
        editor.apply();
    }

    public void salvarSomaCartas(String soma) {
        editor.putString(RESULT_KEY, soma);
        editor.apply();
    }

    public String recuperarSomaCartas() {
        return preferences.getString(RESULT_KEY, null);
    }

    public void removerSomaCartas() {
        editor.remove(RESULT_KEY);
        editor.apply();
    }

    public void removerCartasEscolhidas() {
        editor.remove(RANDOM_LIST_CODE_KEY);
        editor.remove(RANDOM_LIST_IMAGE_KEY);
        editor.remove(RANDOM_LIST_VALUE_KEY);
        editor.remove(RANDOM_LIST_SUIT_KEY);
        editor.apply();
    }

    public List<String> recuperarCartasEcolhidas() {
        String codigo = preferences.getString(RANDOM_LIST_CODE_KEY, null);
        String imagem = preferences.getString(RANDOM_LIST_IMAGE_KEY, null);
        String valor = preferences.getString(RANDOM_LIST_VALUE_KEY, null);
        String naipe = preferences.getString(RANDOM_LIST_SUIT_KEY, null);
        if (codigo == null || imagem == null || valor == null || naipe == null) {
            return new ArrayList<>();
        }
        Type type = new TypeToken<List<String>>() {
        }.getType();

        List<String> listDeckCode = gson.fromJson(codigo, type);
        List<String> listDeckImage = gson.fromJson(imagem, type);
        List<String> listDeckValue = gson.fromJson(valor, type);
        List<String> listDeckSuit = gson.fromJson(naipe, type);

        List<String> listDeckCards = new ArrayList<>();
        for (int i = 0; i < listDeckCode.size(); i++) {
            listDeckCards.add(listDeckCode.get(i));
            listDeckCards.add(listDeckImage.get(i));
            listDeckCards.add(listDeckValue.get(i));
            listDeckCards.add(listDeckSuit.get(i));
        }
        return listDeckCards;
    }
}