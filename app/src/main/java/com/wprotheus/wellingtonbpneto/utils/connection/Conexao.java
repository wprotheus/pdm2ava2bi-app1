package com.wprotheus.wellingtonbpneto.utils.connection;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Conexao {
    public InputStream obterRespostaHTTP(String address) throws MalformedURLException {
        try {
            URL url = new URL(address);
            HttpURLConnection conexao = (HttpURLConnection) url.openConnection();
            conexao.setRequestMethod("GET");
            return new BufferedInputStream(conexao.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}