package com.wprotheus.wellingtonbpneto.viewmodel;

import com.google.gson.Gson;
import com.wprotheus.wellingtonbpneto.model.Baralho;
import com.wprotheus.wellingtonbpneto.utils.connection.Auxiliar;
import com.wprotheus.wellingtonbpneto.utils.connection.Conexao;

import java.io.InputStream;

public class DataSetCartas {
    private final String URL = "https://www.deckofcardsapi.com/api/deck/new/draw/?count=52";

    public Baralho baixarJSON() {
        try {
            Conexao conexao = new Conexao();
            InputStream inputStream = conexao.obterRespostaHTTP(URL);
            Auxiliar auxiliar = new Auxiliar();
            String textoFromJson = auxiliar.converter(inputStream);
            if (!textoFromJson.isEmpty()) {
                return new Gson().fromJson(textoFromJson, Baralho.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}